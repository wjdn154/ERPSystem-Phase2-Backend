package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateAccountNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateMediumCategoryNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateSmallCategoryNode;
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
        Map<String, FinancialStateMediumCategoryNode> root = new LinkedHashMap<>();

        for (FinancialStatementsLedgerDTO data : queryResults) {
            String mediumCategoryName = data.getMediumCategory(); // 중분류 카테고리 이름
            String smallCategoryName = data.getSmallCategory();   // 소분류 카테고리 이름
            String statementsName = data.getFinancialStatementsName(); // 계정명
            int mediumStructureMin = data.getAccountStructureMin();    // 중분류 구조 코드 최소값
            int smallStructureMin = Integer.parseInt(data.getAccountStructureCode()); // 소분류 구조 코드 최소값
            int accountStructureMin = Integer.parseInt(data.getFinancialStatementsCode()); // 계정 구조 코드

            // 중분류와 소분류가 동일한 경우 처리
            if (mediumCategoryName.equals(smallCategoryName)) {
                smallCategoryName = null;
                smallStructureMin = mediumStructureMin;
            }

            // 중분류 노드 가져오기 또는 생성
            FinancialStateMediumCategoryNode mediumNode = root.get(mediumCategoryName);
            if (mediumNode == null) {
                mediumNode = new FinancialStateMediumCategoryNode(mediumCategoryName, mediumStructureMin);
                root.put(mediumCategoryName, mediumNode);
            }

            // 중분류 노드에 값 추가
            mediumNode.addValues(data);

            FinancialStateNode parentNode;

            if (smallCategoryName != null) {
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
            FinancialStateAccountNode accountNode = new FinancialStateAccountNode(statementsName, accountStructureMin);
            accountNode.addValues(data);
            parentNode.addChild(accountNode);
        }

// 트리를 순회하여 FinancialStatementsLedgerShowDTO 리스트로 변환
        List<FinancialStatementsLedgerShowDTO> result = new ArrayList<>();
        List<FinancialStateMediumCategoryNode> sortedMediumNodes = new ArrayList<>(root.values());
        Collections.sort(sortedMediumNodes);

// 중분류와 대분류의 매핑 설정
        Map<String, String> mediumToLargeCategoryMap = new HashMap<>();
        mediumToLargeCategoryMap.put("유동자산", "자산총계");
        mediumToLargeCategoryMap.put("비유동자산", "자산총계");
        mediumToLargeCategoryMap.put("유동부채", "부채총계");
        mediumToLargeCategoryMap.put("비유동부채", "부채총계");
// 기타 중분류는 "자본총계"로 처리

// 대분류별 합계를 저장할 맵 초기화
        Map<String, MediumTotal> largeCategoryTotals = new HashMap<>();

// 대분류별 중분류 리스트를 저장할 맵 초기화
        Map<String, List<String>> largeToMediumCategories = new LinkedHashMap<>();

// 중분류 노드를 순회하며 대분류별 중분류 리스트 구성
        for (FinancialStateMediumCategoryNode mediumNode : sortedMediumNodes) {
            String mediumCategoryName = mediumNode.getName();
            String largeCategoryName = mediumToLargeCategoryMap.getOrDefault(mediumCategoryName, "자본총계");

            // 대분류별 중분류 리스트에 추가
            largeToMediumCategories.computeIfAbsent(largeCategoryName, k -> new ArrayList<>()).add(mediumCategoryName);
        }

// 중분류 노드를 순회하며 결과 리스트 구성
        for (FinancialStateMediumCategoryNode mediumNode : sortedMediumNodes) {
            String mediumCategoryName = mediumNode.getName();
            String largeCategoryName = mediumToLargeCategoryMap.getOrDefault(mediumCategoryName, "자본총계");

            result.add(FinancialStatementsLedgerShowDTO.create(mediumNode, "Medium_category"));

            // 대분류 합계 가져오기 또는 생성
            MediumTotal totals = largeCategoryTotals.computeIfAbsent(largeCategoryName, k -> new MediumTotal());
            totals.add(mediumNode);

            // 자식 노드 처리
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

            // 현재 중분류가 해당 대분류의 마지막 중분류인지 확인
            List<String> mediumCategoriesInLarge = largeToMediumCategories.get(largeCategoryName);
            if (mediumCategoriesInLarge != null && mediumCategoryName.equals(mediumCategoriesInLarge.get(mediumCategoriesInLarge.size() - 1))) {
                // 대분류 합계를 결과에 추가
                MediumTotal largeTotals = largeCategoryTotals.get(largeCategoryName);
                if (largeTotals != null) {
                    result.add(FinancialStatementsLedgerShowDTO.create(
                            "Large_Category",
                            largeCategoryName,
                            largeTotals.totalDebitAmount,
                            largeTotals.totalCreditAmount
                    ));
                }
            }
        }

// "부채와 자본총계" 계산 및 추가
        MediumTotal liabilitiesTotals = largeCategoryTotals.get("부채총계");
        MediumTotal equityTotals = largeCategoryTotals.get("자본총계");

        MediumTotal totalLiabilitiesAndEquity = new MediumTotal();
        if (liabilitiesTotals != null) {
            totalLiabilitiesAndEquity.add(liabilitiesTotals);
        }
        if (equityTotals != null) {
            totalLiabilitiesAndEquity.add(equityTotals);
        }

        result.add(FinancialStatementsLedgerShowDTO.create(
                "Large_Category",
                "부채와 자본총계",
                totalLiabilitiesAndEquity.totalDebitAmount.add(BigDecimal.valueOf(1000000)),
                totalLiabilitiesAndEquity.totalCreditAmount
        ));

// 결과 반환
        return result;
    }
}


class MediumTotal {
    BigDecimal totalDebitBalance = BigDecimal.ZERO;
    BigDecimal totalDebitAmount = BigDecimal.ZERO;
    BigDecimal totalCreditBalance = BigDecimal.ZERO;
    BigDecimal totalCreditAmount = BigDecimal.ZERO;

    public void add(FinancialStateMediumCategoryNode node) {
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
