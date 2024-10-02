package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.CustomNode.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.enums.DailyAndMonthType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyAndMonthJournalServiceImpl implements DailyAndMonthJournalService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;


    @Override
    public List<DailyAndMonthJournalShowDTO> dailyLedgerShow(DailyAndMonthJournalSearchDTO dto) {
        List<DailyAndMonthJournalDTO> queryResults = resolvedVoucherRepository.dailyLedgerList(dto);

        // 트리 구조 생성
        Map<String, MediumCategoryNode> root = new LinkedHashMap<>();

        for (DailyAndMonthJournalDTO data : queryResults) {
            String mediumCategoryName = data.getAccountStructureMediumCategory();
            String smallCategoryName = data.getAccountStructureSmallCategory();
            String accountName = data.getAccountName();
            int mediumStructureMin = data.getAccountStructureMin();
            int smallStructureMin = Integer.parseInt(data.getAccountStructureCode());
            int accountStructureMin = Integer.parseInt(data.getAccountCode());

            // Medium과 Small 카테고리가 동일하면 하나로 처리
            if (mediumCategoryName.equals(smallCategoryName)) {
                smallCategoryName = null;
                smallStructureMin = mediumStructureMin;
            }

            // MediumCategoryNode 가져오기 또는 생성
            MediumCategoryNode mediumNode = root.get(mediumCategoryName);
            if (mediumNode == null) {
                mediumNode = new MediumCategoryNode(mediumCategoryName, mediumStructureMin);
                root.put(mediumCategoryName, mediumNode);
            }

            // MediumCategoryNode에 합계 추가
            mediumNode.addValues(data);

            CustomNode parentNode;

            if (smallCategoryName != null) {
                // SmallCategoryNode 가져오기 또는 생성
                SmallCategoryNode smallNode = null;
                for (CustomNode node : mediumNode.getChildren()) {
                    if (node.getName().equals(smallCategoryName)) {
                        smallNode = (SmallCategoryNode) node;
                        break;
                    }
                }
                if (smallNode == null) {
                    smallNode = new SmallCategoryNode(smallCategoryName, smallStructureMin);
                    mediumNode.addChild(smallNode);
                }
                // SmallCategoryNode에 합계 추가
                smallNode.addValues(data);
                parentNode = smallNode;
            } else {
                // Small Category가 없으면 MediumCategoryNode가 부모 노드
                parentNode = mediumNode;
            }

            // AccountNode 생성 및 추가
            AccountNode accountNode = new AccountNode(accountName, accountStructureMin);
            accountNode.addValues(data);
            parentNode.addChild(accountNode);
        }

        // 트리를 순회하여 하나의 리스트로 변환
        List<DailyAndMonthJournalShowDTO> result = new ArrayList<>();
        List<MediumCategoryNode> sortedMediumNodes = new ArrayList<>(root.values());
        Collections.sort(sortedMediumNodes);

        // 금일소계를 계산하기 위한 변수 초기화
        BigDecimal totalCashTotalDebit = BigDecimal.ZERO;
        BigDecimal totalSubTotalDebit = BigDecimal.ZERO;
        BigDecimal totalSumTotalDebit = BigDecimal.ZERO;
        BigDecimal totalCashTotalCredit = BigDecimal.ZERO;
        BigDecimal totalSubTotalCredit = BigDecimal.ZERO;
        BigDecimal totalSumTotalCredit = BigDecimal.ZERO;

        for (MediumCategoryNode mediumNode : sortedMediumNodes) {
            result.add(DailyAndMonthJournalShowDTO.create(mediumNode, "Medium_category"));

            // 금일소계 누적
            totalCashTotalDebit = totalCashTotalDebit.add(mediumNode.getCashTotalDebit());
            totalSubTotalDebit = totalSubTotalDebit.add(mediumNode.getSubTotalDebit());
            totalSumTotalDebit = totalSumTotalDebit.add(mediumNode.getSumTotalDebit());
            totalCashTotalCredit = totalCashTotalCredit.add(mediumNode.getCashTotalCredit());
            totalSubTotalCredit = totalSubTotalCredit.add(mediumNode.getSubTotalCredit());
            totalSumTotalCredit = totalSumTotalCredit.add(mediumNode.getSumTotalCredit());

            List<CustomNode> sortedSmallNodes = mediumNode.getChildren();
            for (CustomNode childNode : sortedSmallNodes) {
                if (childNode instanceof SmallCategoryNode) {
                    result.add(DailyAndMonthJournalShowDTO.create(childNode, "Small_category"));
                    List<CustomNode> sortedAccountNodes = childNode.getChildren();
                    for (CustomNode accountNode : sortedAccountNodes) {
                        result.add(DailyAndMonthJournalShowDTO.create(accountNode, "Account_name"));
                    }
                } else if (childNode instanceof AccountNode) {
                    result.add(DailyAndMonthJournalShowDTO.create(childNode, "Account_name"));
                }
            }
        }

        // 루프 종료 후, 금일소계, 잔고, 합계를 한 번만 추가
        String typeName;
        if (dto.getJournalType().equals(DailyAndMonthType.DAILY)) {
            typeName = "일";
        } else {
            typeName = "월";
        }

        // 금일소계 추가
        DailyAndMonthJournalShowDTO todaySubtotalDTO = new DailyAndMonthJournalShowDTO();
        todaySubtotalDTO.setName("금" + typeName + "소계");
        todaySubtotalDTO.setCashTotalDebit(totalCashTotalDebit);
        todaySubtotalDTO.setSubTotalDebit(totalSubTotalDebit);
        todaySubtotalDTO.setSumTotalDebit(totalSumTotalDebit);
        todaySubtotalDTO.setCashTotalCredit(totalCashTotalCredit);
        todaySubtotalDTO.setSubTotalCredit(totalSubTotalCredit);
        todaySubtotalDTO.setSumTotalCredit(totalSumTotalCredit);
        todaySubtotalDTO.setLevel(null); // Level을 null로 설정

        result.add(todaySubtotalDTO);

        // 금일잔고/전일잔고: 임시 금액 설정 (예: BigDecimal.ZERO)
        BigDecimal previousBalanceSubTotalDebit = BigDecimal.ZERO; // 전일 잔고 차변 (임시 값)
        BigDecimal previousBalanceCashTotalDebit = BigDecimal.valueOf(1355000); // 전일 잔고 차변 (임시 값)
        BigDecimal previousBalanceSumTotalDebit = previousBalanceSubTotalDebit.add(previousBalanceCashTotalDebit);
        BigDecimal previousBalanceSubTotalCredit = BigDecimal.ZERO; // 전일 잔고 대변 (임시 값)
        BigDecimal previousBalanceCashTotalCredit = BigDecimal.valueOf(272000); // 전일 잔고 대변 (임시 값)
        BigDecimal previousBalanceSumTotalCredit = previousBalanceSubTotalCredit.add(previousBalanceCashTotalCredit);

        // 금일잔고/전일잔고 추가
        DailyAndMonthJournalShowDTO balanceDTO = new DailyAndMonthJournalShowDTO();
        balanceDTO.setName("금" + typeName + "잔고/전" + typeName + " 잔고");
        balanceDTO.setCashTotalDebit(previousBalanceCashTotalDebit);
        balanceDTO.setSubTotalDebit(previousBalanceSubTotalDebit);
        balanceDTO.setSumTotalDebit(previousBalanceSumTotalDebit);
        balanceDTO.setCashTotalCredit(previousBalanceCashTotalCredit);
        balanceDTO.setSubTotalCredit(previousBalanceSubTotalCredit);
        balanceDTO.setSumTotalCredit(previousBalanceSumTotalCredit);
        balanceDTO.setLevel(null);

        result.add(balanceDTO);

        // 합계 계산: 금일소계 + 금일잔고/전일잔고
        DailyAndMonthJournalShowDTO totalDTO = new DailyAndMonthJournalShowDTO();
        totalDTO.setName("합계");
        totalDTO.setCashTotalDebit(totalCashTotalDebit.add(previousBalanceCashTotalDebit));
        totalDTO.setSubTotalDebit(totalSubTotalDebit.add(previousBalanceSubTotalDebit));
        totalDTO.setSumTotalDebit(totalSumTotalDebit.add(previousBalanceSumTotalDebit));
        totalDTO.setCashTotalCredit(totalCashTotalCredit.add(previousBalanceCashTotalCredit));
        totalDTO.setSubTotalCredit(totalSubTotalCredit.add(previousBalanceSubTotalCredit));
        totalDTO.setSumTotalCredit(totalSumTotalCredit.add(previousBalanceSumTotalCredit));
        totalDTO.setLevel(null);

        result.add(totalDTO);

        // 결과 반환
        return result;
    }


}
