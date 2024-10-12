package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher.ResolvedSaleAndPurchaseVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxInvoiceLedgerServiceImpl implements TaxInvoiceLedgerService {

    private final ResolvedSaleAndPurchaseVoucherRepository resolvedSaleAndPurchaseVoucherRepository;

    @Override
//    public List<TaxInvoiceLedgerShowDTO> show(TaxInvoiceLedgerSearchDTO dto) {
//        List<TaxInvoiceLedgerShowDTO> resultList = resolvedSaleAndPurchaseVoucherRepository.showTaxInvoiceLedger(dto);
//
//// 거래처별로 그룹화 (거래처 코드 + 거래처명 + 거래처 번호 기준)
//        Map<String, List<TaxInvoiceLedgerShowDTO>> groupedData = resultList.stream()
//                .collect(Collectors.groupingBy(dto2 -> dto2.getClientCode() + "-" + dto2.getClientName() + "-" + dto2.getClientNumber()));
//
//        List<TaxInvoiceLedgerShowDTO> finalList = new ArrayList<>();
//
//// 각 거래처별로 데이터를 처리
//        for (Map.Entry<String, List<TaxInvoiceLedgerShowDTO>> entry : groupedData.entrySet()) {
//            List<TaxInvoiceLedgerShowDTO> groupedList = entry.getValue();
//
//            // 전체 합계 계산
//            int totalVoucherCount = groupedList.stream().mapToInt(TaxInvoiceLedgerShowDTO::getVoucherCount).sum();
//            BigDecimal totalSupplyAmount = groupedList.stream()
//                    .map(TaxInvoiceLedgerShowDTO::getSupplyAmount)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//            BigDecimal totalVatAmount = groupedList.stream()
//                    .map(TaxInvoiceLedgerShowDTO::getVatAmount)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            // 거래처별 합계 정보 추가
//            TaxInvoiceLedgerShowDTO summaryDTO = new TaxInvoiceLedgerShowDTO(
//                    groupedList.get(0).getClientCode(),
//                    groupedList.get(0).getClientName(),
//                    groupedList.get(0).getClientNumber(),
//                    100, // 전체를 나타내는 식별자 (필요시 수정)
//                    totalVoucherCount,
//                    totalSupplyAmount,
//                    totalVatAmount
//            );
//
//            finalList.add(summaryDTO);
//
//            // 월별로 그룹화해서 처리 (월별 데이터 계산)
//            Map<Integer, List<TaxInvoiceLedgerShowDTO>> monthlyMap = groupedList.stream()
//                    .collect(Collectors.groupingBy(TaxInvoiceLedgerShowDTO::getMonth)); // 월을 기준으로 그룹화
//
//            for (Map.Entry<Integer, List<TaxInvoiceLedgerShowDTO>> monthEntry : monthlyMap.entrySet()) {
//                int month = monthEntry.getKey();
//                List<TaxInvoiceLedgerShowDTO> monthList = monthEntry.getValue();
//
//                // 월별 합계 계산
//                int monthVoucherCount = monthList.stream().mapToInt(TaxInvoiceLedgerShowDTO::getVoucherCount).sum();
//                BigDecimal monthSupplyAmount = monthList.stream()
//                        .map(TaxInvoiceLedgerShowDTO::getSupplyAmount)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add);
//                BigDecimal monthVatAmount = monthList.stream()
//                        .map(TaxInvoiceLedgerShowDTO::getVatAmount)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//                // 월별 합계 정보 추가
//                TaxInvoiceLedgerShowDTO monthSummaryDTO = new TaxInvoiceLedgerShowDTO(
//                        groupedList.get(0).getClientCode(),
//                        groupedList.get(0).getClientName(),
//                        groupedList.get(0).getClientNumber(),
//                        month, // 월을 나타내는 식별자 (필요시 수정)
//                        monthVoucherCount,
//                        monthSupplyAmount,
//                        monthVatAmount
//                );
//
//                // 월별 합계만 추가
//                finalList.add(monthSummaryDTO);
//            }
//        }
//// 최종 결과 반환
//        return finalList;
//    }


    public List<TaxInvoiceLedgerShowDTO> show(TaxInvoiceLedgerSearchDTO dto) {
        List<TaxInvoiceLedgerShowDTO> resultList = resolvedSaleAndPurchaseVoucherRepository.showTaxInvoiceLedger(dto);

// 거래처별로 그룹화 (거래처 코드 + 거래처명 + 거래처 번호 기준)
        Map<String, List<TaxInvoiceLedgerShowDTO>> groupedData = resultList.stream()
                .collect(Collectors.groupingBy(dto2 -> dto2.getClientCode() + "-" + dto2.getClientName() + "-" + dto2.getClientNumber()));

        List<TaxInvoiceLedgerShowDTO> finalList = new ArrayList<>();

// 최종 리스트에 모든 거래처의 총합계를 추가하는 로직 추가
        BigDecimal grandTotalSupply = BigDecimal.ZERO;
        BigDecimal grandTotalVat = BigDecimal.ZERO;
        int grandTotalVoucherCount = 0;

// 월별 총합계도 계산하기 위해서 사용할 맵
        Map<Integer, BigDecimal> monthlyTotalSupply = new HashMap<>();
        Map<Integer, BigDecimal> monthlyTotalVat = new HashMap<>();
        Map<Integer, Integer> monthlyTotalVoucherCount = new HashMap<>();

        for (Map.Entry<String, List<TaxInvoiceLedgerShowDTO>> entry : groupedData.entrySet()) {
            List<TaxInvoiceLedgerShowDTO> groupedList = entry.getValue();

            // 거래처별 총합계 계산
            int totalVoucherCount = groupedList.stream().mapToInt(TaxInvoiceLedgerShowDTO::getVoucherCount).sum();
            BigDecimal totalSupplyAmount = groupedList.stream()
                    .map(TaxInvoiceLedgerShowDTO::getSupplyAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalVatAmount = groupedList.stream()
                    .map(TaxInvoiceLedgerShowDTO::getVatAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 거래처별 합계 정보 추가
            TaxInvoiceLedgerShowDTO summaryDTO = new TaxInvoiceLedgerShowDTO(
                    groupedList.get(0).getClientCode(),
                    groupedList.get(0).getClientName(),
                    groupedList.get(0).getClientNumber(),
                    100, // 전체를 나타내는 식별자 (필요시 수정)
                    totalVoucherCount,
                    totalSupplyAmount,
                    totalVatAmount
            );

            finalList.add(summaryDTO);

            // 월별 합계 처리
            Map<Integer, List<TaxInvoiceLedgerShowDTO>> monthlyMap = groupedList.stream()
                    .collect(Collectors.groupingBy(TaxInvoiceLedgerShowDTO::getMonth));

            for (Map.Entry<Integer, List<TaxInvoiceLedgerShowDTO>> monthEntry : monthlyMap.entrySet()) {
                int month = monthEntry.getKey();
                List<TaxInvoiceLedgerShowDTO> monthList = monthEntry.getValue();

                // 월별 합계 계산
                int monthVoucherCount = monthList.stream().mapToInt(TaxInvoiceLedgerShowDTO::getVoucherCount).sum();
                BigDecimal monthSupplyAmount = monthList.stream()
                        .map(TaxInvoiceLedgerShowDTO::getSupplyAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal monthVatAmount = monthList.stream()
                        .map(TaxInvoiceLedgerShowDTO::getVatAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 월별 합계 추가
                TaxInvoiceLedgerShowDTO monthSummaryDTO = new TaxInvoiceLedgerShowDTO(
                        groupedList.get(0).getClientCode(),
                        groupedList.get(0).getClientName(),
                        groupedList.get(0).getClientNumber(),
                        month,
                        monthVoucherCount,
                        monthSupplyAmount,
                        monthVatAmount
                );

                finalList.add(monthSummaryDTO);

                // 월별 총합계 계산
                monthlyTotalSupply.put(month, monthlyTotalSupply.getOrDefault(month, BigDecimal.ZERO).add(monthSupplyAmount));
                monthlyTotalVat.put(month, monthlyTotalVat.getOrDefault(month, BigDecimal.ZERO).add(monthVatAmount));
                monthlyTotalVoucherCount.put(month, monthlyTotalVoucherCount.getOrDefault(month, 0) + monthVoucherCount);

                // 총합계에 추가
                grandTotalSupply = grandTotalSupply.add(monthSupplyAmount);
                grandTotalVat = grandTotalVat.add(monthVatAmount);
                grandTotalVoucherCount += monthVoucherCount;
            }
        }

// 각 월별 총합계 추가
        for (Map.Entry<Integer, BigDecimal> monthEntry : monthlyTotalSupply.entrySet()) {
            int month = monthEntry.getKey();
            TaxInvoiceLedgerShowDTO monthlyTotalDTO = new TaxInvoiceLedgerShowDTO(
                    "전체",
                    "전체",
                    "전체",
                    month,
                    monthlyTotalVoucherCount.get(month),
                    monthlyTotalSupply.get(month),
                    monthlyTotalVat.get(month)
            );
            finalList.add(monthlyTotalDTO);
        }

// 최종 총합계 추가
        TaxInvoiceLedgerShowDTO grandTotalDTO = new TaxInvoiceLedgerShowDTO(
                "전체",
                "전체",
                "전체",
                100,  // 전체 식별자
                grandTotalVoucherCount,
                grandTotalSupply,
                grandTotalVat
        );

        finalList.add(grandTotalDTO);

        finalList.sort(Comparator
                .comparing(TaxInvoiceLedgerShowDTO::getClientCode)  // clientCode 기준으로 먼저 정렬
                .thenComparing(dto2 -> dto2.getMonth() == 100 ? Integer.MIN_VALUE : dto2.getMonth()));  // Month가 100인 항목은 제일 앞으로

// 최종 결과 반환
        return finalList;

}
}
