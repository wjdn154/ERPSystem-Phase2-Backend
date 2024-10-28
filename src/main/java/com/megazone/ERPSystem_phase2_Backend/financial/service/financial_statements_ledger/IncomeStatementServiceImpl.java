package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.IncomeStatementLedgerPrintType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerDashBoardDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeStatementServiceImpl implements IncomeStatementService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<IncomeStatementLedgerShowDTO> show(IncomeStatementSearchDTO dto) {
        // 1. 쿼리 결과 가져오기
        List<IncomeStatementLedgerDTO> queryResults = resolvedVoucherRepository.incomeStatementShow(dto);
        return createIncomeStatementLedger(queryResults,IncomeStatementLedgerPrintType.GENERAL);
    }

    @Override
    public IncomeStatementLedgerDashBoardDTO DashBoardShow() {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        List<List<IncomeStatementLedgerShowDTO>> result = new ArrayList<>();

        // 현재 날짜를 기준으로 현재 연도의 1월부터 현재 월까지 반복
        YearMonth startMonth = YearMonth.of(LocalDate.now().getYear(), 1);  // 1월부터 시작
        YearMonth currentMonth = YearMonth.from(LocalDate.now());  // 현재 연도와 월 가져오기


        for (YearMonth month = startMonth; !month.isAfter(currentMonth); month = month.plusMonths(1)) {

            List<IncomeStatementLedgerDTO> queryResults = resolvedVoucherRepository.incomeStatementShow(
                    IncomeStatementSearchDTO.create(month));
            List<IncomeStatementLedgerShowDTO> monthList = createIncomeStatementLedger(queryResults,IncomeStatementLedgerPrintType.DASHBOARD);
            totalRevenue = totalRevenue.add(monthList.get(0).getTotalAmount());
            totalExpense = totalExpense.add(monthList.get(1).getTotalAmount());
            result.add(monthList);
        }
        return IncomeStatementLedgerDashBoardDTO.create(result,totalRevenue,totalExpense);
    }

    public List<IncomeStatementLedgerShowDTO> createIncomeStatementLedger(List<IncomeStatementLedgerDTO> queryResults, IncomeStatementLedgerPrintType type) {
        // 2. 트리 구조 생성
        Map<String, IncomeStatementMediumCategoryNode> root = new LinkedHashMap<>();

        for (IncomeStatementLedgerDTO data : queryResults) {
            String mediumCategoryName = data.getMediumCategory(); // 중분류 이름
            String smallCategoryName = data.getSmallCategory();   // 소분류 이름
            String accountName = data.getFinancialStatementsName(); // 계정명
            int mediumStructureMin = data.getAccountStructureMin(); // 중분류 구조 최소값
            int smallStructureMin = Integer.parseInt(data.getFinancialStatementsCode()); // 소분류 구조 최소값

            // Medium과 Small 카테고리가 동일하면 하나로 처리
            if (mediumCategoryName.equals(smallCategoryName)) {
                smallCategoryName = null;
                smallStructureMin = mediumStructureMin;
            }

            // 3. 중분류 노드 가져오기 또는 생성
            IncomeStatementMediumCategoryNode mediumNode = root.get(mediumCategoryName);
            if (mediumNode == null) {
                mediumNode = new IncomeStatementMediumCategoryNode(mediumCategoryName, mediumStructureMin);
                root.put(mediumCategoryName, mediumNode);
            }
            mediumNode.addValues(data); // 중분류 노드에 값 추가

            IncomeStateNode parentNode;

            // 4. 소분류 노드 가져오기 또는 생성
            if(smallCategoryName != null) {
                IncomeStatementSmallCategoryNode smallNode = null;

                for (IncomeStateNode node : mediumNode.getChildren()) {
                    if (node.getName().equals(smallCategoryName)) {
                        smallNode = (IncomeStatementSmallCategoryNode) node;
                        break;
                    }
                }
                if (smallNode == null) {
                    smallNode = new IncomeStatementSmallCategoryNode(smallCategoryName, smallStructureMin);
                    mediumNode.addChild(smallNode);
                }
                smallNode.addValues(data); // 소분류 노드에 값 추가
                parentNode = smallNode;
            } else {
                parentNode = mediumNode;
            }

            // 5. 계정 노드 생성 및 추가
            IncomeStatementAccountNode accountNode = new IncomeStatementAccountNode(accountName, data.getFinancialStatementId().intValue());
            accountNode.addValues(data);
            parentNode.addChild(accountNode);
        }



        // 6. 트리를 순회하여 IncomeStatementLedgerShowDTO 리스트로 변환
        List<IncomeStatementLedgerShowDTO> result = new ArrayList<>();
        List<IncomeStatementMediumCategoryNode> sortedMediumNodes = new ArrayList<>(root.values());
        Collections.sort(sortedMediumNodes);

        Map<String, IncomeMediumTotal> largeCategoryTotals = new HashMap<>();

        for (IncomeStatementMediumCategoryNode mediumNode : sortedMediumNodes) {
            IncomeMediumTotal totals = largeCategoryTotals.computeIfAbsent(mediumNode.getName(), k -> new IncomeMediumTotal());
            totals.addTotal(mediumNode);

            if(type.equals(IncomeStatementLedgerPrintType.GENERAL)) {
                result.add(IncomeStatementLedgerShowDTO.create(mediumNode, "Medium_Category"));

                List<IncomeStateNode> sortedSmallNodes = mediumNode.getChildren();
                for (IncomeStateNode smallNode : sortedSmallNodes) {
                    if(smallNode instanceof IncomeStatementSmallCategoryNode) {
                        result.add(IncomeStatementLedgerShowDTO.create(smallNode, "Small_Category"));

                        List<IncomeStateNode> sortedAccountNodes = smallNode.getChildren();
                        for (IncomeStateNode accountNode : sortedAccountNodes) {
                            result.add(IncomeStatementLedgerShowDTO.create(accountNode, "Account_Name"));
                        }
                    }else if(smallNode instanceof IncomeStatementAccountNode) {
                        result.add(IncomeStatementLedgerShowDTO.create(smallNode, "Account_Name"));
                    }
                }
            }
        }
        if(type.equals(IncomeStatementLedgerPrintType.GENERAL)) {
            setIncomeStatementPrint(result,largeCategoryTotals);
        }
        else {
            setIncomeStatementDashBoardPrint(result, largeCategoryTotals);
        }
        return result;
    }

    class IncomeMediumTotal {
        BigDecimal totalAmount = BigDecimal.ZERO;

        public void addTotal(IncomeStatementMediumCategoryNode mediumNode) {
            totalAmount = totalAmount.add(mediumNode.getTotalAmount());
        }

        public void addTotal(IncomeMediumTotal other) {
            totalAmount = totalAmount.add(other.totalAmount);
        }

        public void subtractTotal(IncomeMediumTotal other) {
            totalAmount = totalAmount.subtract(other.totalAmount);
        }
    }

    private void setIncomeStatementPrint(List<IncomeStatementLedgerShowDTO> result, Map<String, IncomeMediumTotal> LargeTotalCategory) {
        IncomeMediumTotal grossProfit = new IncomeMediumTotal();  // 매출총손익
        grossProfit.addTotal(LargeTotalCategory.get("매출")); //43750000
        grossProfit.subtractTotal(LargeTotalCategory.get("매출원가")); //7902200
        result.add(IncomeStatementLedgerShowDTO.create(
                "Large_Category",
                "매출총손익",
                grossProfit.totalAmount
        ));

        IncomeMediumTotal operatingProfit = new IncomeMediumTotal(); ; // 영업손익
        operatingProfit.addTotal(grossProfit);
        operatingProfit.subtractTotal(LargeTotalCategory.get("판매관리비")); //7034100
        result.add(IncomeStatementLedgerShowDTO.create(
                "Large_Category",
                "영업손익",
                operatingProfit.totalAmount
        ));

        IncomeMediumTotal profitBeforeTax= new IncomeMediumTotal(); // 법인세비용차감전손익
        profitBeforeTax.addTotal(operatingProfit);
        profitBeforeTax.addTotal(LargeTotalCategory.get("영업외수익")); //0
        profitBeforeTax.subtractTotal(LargeTotalCategory.get("영업외비용")); //131000
        result.add(IncomeStatementLedgerShowDTO.create(
                "Large_Category",
                "법인세비용차감전손익",
                profitBeforeTax.totalAmount
        ));

        IncomeMediumTotal netProfit = new IncomeMediumTotal();  // 당기순손익
        netProfit.addTotal(profitBeforeTax);
        netProfit.subtractTotal(LargeTotalCategory.get("법인세")); //0
        result.add(IncomeStatementLedgerShowDTO.create(
                "Large_Category",
                "당기순손익",
                netProfit.totalAmount
        ));
    }

    private void setIncomeStatementDashBoardPrint(List<IncomeStatementLedgerShowDTO> result, Map<String, IncomeMediumTotal> LargeTotalCategory) {
        IncomeMediumTotal grossProfit = new IncomeMediumTotal(); // 매출
        grossProfit.addTotal(LargeTotalCategory.get("매출"));
        result.add(IncomeStatementLedgerShowDTO.create(
                "Large_Category",
                "매출",
                grossProfit.totalAmount
        ));

        IncomeMediumTotal operatingProfit = new IncomeMediumTotal(); // 비용
        operatingProfit.addTotal(LargeTotalCategory.get("매출원가"));
        operatingProfit.addTotal(LargeTotalCategory.get("판매관리비"));
        operatingProfit.addTotal(LargeTotalCategory.get("영업외비용"));
        operatingProfit.addTotal(LargeTotalCategory.get("법인세"));
        result.add(IncomeStatementLedgerShowDTO.create(
                "Large_Category",
                "비용",
                operatingProfit.totalAmount
        ));
    }
}
