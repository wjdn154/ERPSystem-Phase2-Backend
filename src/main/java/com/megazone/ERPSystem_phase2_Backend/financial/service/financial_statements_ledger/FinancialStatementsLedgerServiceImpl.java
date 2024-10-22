package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class FinancialStatementsLedgerServiceImpl implements FinancialStatementsLedgerService {

    private final ResolvedVoucherRepository resolvedVoucherRepository;


    /**
     * 표준재무재표 출력 로직
     * 자본총계, 부채총계, 합계 방식 필요
     */
    @Override
    public List<FinancialStatementsLedgerShowDTO> show(FinancialStatementsLedgerSearchDTO dto) {
        List<FinancialStatementsLedgerDTO> queryResults = resolvedVoucherRepository.financialStatementsShow(dto);
// 트리 구조 생성
        Map<String, FinancialStateLargeCategoryNode> root = new LinkedHashMap<>();

        for (FinancialStatementsLedgerDTO data : queryResults) {
            setTotalDebitAndCreditBalance(data);
            String largeCategoryName = data.getFinancialStateCategory(); // 대분류 카테고리 이름
            String mediumCategoryName = data.getMediumCategory(); // 중분류 카테고리 이름
            String smallCategoryName = data.getSmallCategory();   // 소분류 카테고리 이름
            String statementsName = data.getFinancialStatementsName(); // 계정명
            int largeStructureMin = data.getAccountStructureMin();    // 대분류 구조 코드 최소값
            int mediumStructureMin = Integer.parseInt(data.getAccountStructureCode()); // 중분류 구조 코드 최소값
            int smallStructureMin = Integer.parseInt(data.getFinancialStatementsCode()); // 소분류 구조 코드

            // 대분류 노드 가져오기 또는 생성
            FinancialStateLargeCategoryNode largeNode = root.get(largeCategoryName);
            if (largeNode == null) {
                largeNode = new FinancialStateLargeCategoryNode(largeCategoryName, largeStructureMin);
                root.put(largeCategoryName, largeNode);
            }

            // 대분류 노드에 값 추가
            largeNode.addValues(data);

            // 중분류 노드 가져오기 또는 생성
            FinancialStateMediumCategoryNode mediumNode = null;
            for (FinancialStateNode node : largeNode.getChildren()) {
                if (node.getName().equals(mediumCategoryName)) {
                    mediumNode = (FinancialStateMediumCategoryNode) node;
                    break;
                }
            }
            if (mediumNode == null) {
                mediumNode = new FinancialStateMediumCategoryNode(mediumCategoryName, mediumStructureMin);
                largeNode.addChild(mediumNode);
            }

            // 중분류 노드에 값 추가
            mediumNode.addValues(data);

            FinancialStateNode parentNode;

            if (smallCategoryName != null && !smallCategoryName.equals(mediumCategoryName)) {
                // 소분류 노드 가져오기 또는 생성
                FinancialStateSmallCategoryNode smallNode = null;
                for (FinancialStateNode node : mediumNode.getChildren()) {
                    if (node.getName().equals(smallCategoryName)) {
                        smallNode = (FinancialStateSmallCategoryNode) node;
                        break;
                    }
                }
                if (smallNode == null) {
                    smallNode = new FinancialStateSmallCategoryNode(smallCategoryName, smallStructureMin);
                    mediumNode.addChild(smallNode);
                }
                // 소분류 노드에 값 추가
                smallNode.addValues(data);
                parentNode = smallNode;
            } else {
                // 소분류가 없으면 중분류 노드가 부모 노드
                parentNode = mediumNode;
            }

            // 계정 노드 생성 및 부모 노드에 추가
            FinancialStateAccountNode accountNode = new FinancialStateAccountNode(statementsName, data.getFinancialStatementId().intValue());
            accountNode.addValues(data);
            parentNode.addChild(accountNode);
        }

// 트리를 순회하여 FinancialStatementsLedgerShowDTO 리스트로 변환
        List<FinancialStatementsLedgerShowDTO> result = new ArrayList<>();
        List<FinancialStateLargeCategoryNode> sortedLargeNodes = new ArrayList<>(root.values());
        Collections.sort(sortedLargeNodes);

// 대분류별 합계를 저장할 맵 초기화
        Map<String, MediumTotal> largeCategoryTotals = new HashMap<>();

        for (FinancialStateLargeCategoryNode largeNode : sortedLargeNodes) {
            // 대분류 합계 계산
            MediumTotal totals = largeCategoryTotals.computeIfAbsent(largeNode.getName(), k -> new MediumTotal());
            totals.add(largeNode);

            // 대분류 표시할라면 주석제거
            // result.add(FinancialStatementsLedgerShowDTO.create(largeNode, "Large_Category"));

            List<FinancialStateNode> sortedMediumNodes = largeNode.getChildren();
            for (FinancialStateNode mediumNode : sortedMediumNodes) {
                result.add(FinancialStatementsLedgerShowDTO.create(mediumNode, "Medium_category"));

                List<FinancialStateNode> sortedSmallNodes = mediumNode.getChildren();
                for (FinancialStateNode childNode : sortedSmallNodes) {
                    if (childNode instanceof FinancialStateSmallCategoryNode) {
                        result.add(FinancialStatementsLedgerShowDTO.create(childNode, "Small_category"));

                        List<FinancialStateNode> sortedAccountNodes = childNode.getChildren();
                        for (FinancialStateNode accountNode : sortedAccountNodes) {
                            result.add(FinancialStatementsLedgerShowDTO.create(accountNode, "Account_name"));
                        }
                    } else if (childNode instanceof FinancialStateAccountNode) {
                        result.add(FinancialStatementsLedgerShowDTO.create(childNode, "Account_name"));
                    }
                }
            }

            // 대분류 합계를 결과에 추가 (각 대분류의 모든 내용을 출력한 후에 합계를 추가)
            result.add(FinancialStatementsLedgerShowDTO.create(
                    "Large_category",
                    largeNode.getName() + " 총계",
                    totals.totalDebitBalance,
                    totals.totalDebitAmount,
                    totals.totalCreditBalance,
                    totals.totalCreditAmount
            ));
        }

// "부채"와 "자본"의 합계를 계산하여 "부채와 자본 총계"를 출력
        MediumTotal liabilitiesTotal = largeCategoryTotals.get("부채"); // "부채" 대분류의 합계
        MediumTotal equityTotal = largeCategoryTotals.get("자본"); // "자본" 대분류의 합계

        MediumTotal liabilitiesAndEquityTotal = new MediumTotal();
        if (liabilitiesTotal != null) {
            liabilitiesAndEquityTotal.add(liabilitiesTotal);
        }
        if (equityTotal != null) {
            liabilitiesAndEquityTotal.add(equityTotal);
        }

// "부채와 자본 총계"를 결과에 추가
        result.add(FinancialStatementsLedgerShowDTO.create(
                "Large_category",
                "부채와 자본 총계",
                liabilitiesAndEquityTotal.totalDebitBalance,
                liabilitiesAndEquityTotal.totalDebitAmount,
                liabilitiesAndEquityTotal.totalCreditBalance,
                liabilitiesAndEquityTotal.totalCreditAmount
        ));

        return result;
    }

    public void setTotalDebitAndCreditBalance(FinancialStatementsLedgerDTO data) {
        if(data.getTotalDebitAmount() == null) {
            data.setTotalDebitAmount(BigDecimal.ZERO);
        }
        if(data.getTotalCreditAmount() == null) {
            data.setTotalCreditAmount(BigDecimal.ZERO);
        }
        BigDecimal totalDebitBalance = BigDecimal.ZERO;
        BigDecimal totalCreditBalance = BigDecimal.ZERO;

        if(data.getFinancialStateCategory().equals("부채") || data.getFinancialStateCategory().equals("자본")) {
            // 차변이 더클경우 음수로 대변잔액에 추가
            // 대변이 더클경우 양수로 대변잔액에 추가
            if(data.getTotalDebitAmount().compareTo(data.getTotalCreditAmount()) == 1) {
                totalCreditBalance = data.getTotalDebitAmount().subtract(data.getTotalCreditAmount()).negate();
            }
            else if(data.getTotalDebitAmount().compareTo(data.getTotalCreditAmount()) == -1) {
                totalCreditBalance = data.getTotalCreditAmount().subtract(data.getTotalDebitAmount());
            }
        }
        else {
            // 차변이 더클경우 차변잔액에 추가
            // 대변이 더클경우 대변잔액에 추가
            if(data.getTotalDebitAmount().compareTo(data.getTotalCreditAmount()) == 1) {
                totalDebitBalance = data.getTotalDebitAmount().subtract(data.getTotalCreditAmount());
            }
            else if(data.getTotalDebitAmount().compareTo(data.getTotalCreditAmount()) == -1) {
                totalCreditBalance = data.getTotalCreditAmount().subtract(data.getTotalDebitAmount());
            }
        }
        data.setTotalDebitBalance(totalDebitBalance);
        data.setTotalCreditBalance(totalCreditBalance);
    }

    class MediumTotal {
        BigDecimal totalDebitBalance = BigDecimal.ZERO;
        BigDecimal totalDebitAmount = BigDecimal.ZERO;
        BigDecimal totalCreditBalance = BigDecimal.ZERO;
        BigDecimal totalCreditAmount = BigDecimal.ZERO;

        public void add(FinancialStateLargeCategoryNode node) {
            totalDebitBalance = totalDebitBalance.add(node.getTotalDebitBalance());
            totalDebitAmount = totalDebitAmount.add(node.getTotalDebitAmount());
            totalCreditBalance = totalCreditBalance.add(node.getTotalCreditBalance());
            totalCreditAmount = totalCreditAmount.add(node.getTotalCreditAmount());
        }

        public void add(MediumTotal other) {
            totalDebitBalance = totalDebitBalance.add(other.totalDebitBalance);
            totalDebitAmount = totalDebitAmount.add(other.totalDebitAmount);
            totalCreditBalance = totalCreditBalance.add(other.totalCreditBalance);
            totalCreditAmount = totalCreditAmount.add(other.totalCreditAmount);
        }
    }

}