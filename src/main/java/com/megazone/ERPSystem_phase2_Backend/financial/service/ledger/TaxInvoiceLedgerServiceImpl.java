package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerShowAllDTO;
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

    /**
     * 거래처의 월별 합계의 합계 month 100으로 표시
     */

    @Override
    public List<TaxInvoiceLedgerShowAllDTO> show(TaxInvoiceLedgerSearchDTO dto) {
        List<TaxInvoiceLedgerShowDTO> resultList = resolvedSaleAndPurchaseVoucherRepository.showTaxInvoiceLedger(dto);

        // 거래처별로 그룹화 (거래처 코드 + 거래처명 + 거래처 번호 기준)
        Map<String, List<TaxInvoiceLedgerShowDTO>> groupedData = resultList.stream()
                .collect(Collectors.groupingBy(dto2 -> dto2.getClientCode() + "-" + dto2.getClientName() + "-" + dto2.getClientNumber()));

        List<TaxInvoiceLedgerShowAllDTO> realFinalList = new ArrayList<>();


        BigDecimal grandTotalSupply = BigDecimal.ZERO;
        BigDecimal grandTotalVat = BigDecimal.ZERO;
        int grandTotalVoucherCount = 0;


        Map<Integer, BigDecimal> monthlyTotalSupply = new HashMap<>();
        Map<Integer, BigDecimal> monthlyTotalVat = new HashMap<>();
        Map<Integer, Integer> monthlyTotalVoucherCount = new HashMap<>();

        for (Map.Entry<String, List<TaxInvoiceLedgerShowDTO>> entry : groupedData.entrySet()) {
            List<TaxInvoiceLedgerShowDTO> groupedList = entry.getValue();

            List<TaxInvoiceLedgerShowDTO> dataList = new ArrayList<>();

            int totalVoucherCount = groupedList.stream().mapToInt(TaxInvoiceLedgerShowDTO::getVoucherCount).sum();
            BigDecimal totalSupplyAmount = groupedList.stream()
                    .map(TaxInvoiceLedgerShowDTO::getSupplyAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalVatAmount = groupedList.stream()
                    .map(TaxInvoiceLedgerShowDTO::getVatAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            TaxInvoiceLedgerShowAllDTO summaryDTO = TaxInvoiceLedgerShowAllDTO.create(
                    groupedList.get(0).getClientCode(),
                    groupedList.get(0).getClientName(),
                    groupedList.get(0).getClientNumber(),
                    totalVoucherCount,
                    totalSupplyAmount,
                    totalVatAmount,
                    new ArrayList<>()
            );

//            finalList.add(summaryDTO);


            Map<Integer, List<TaxInvoiceLedgerShowDTO>> monthlyMap = groupedList.stream()
                    .collect(Collectors.groupingBy(TaxInvoiceLedgerShowDTO::getMonth));

            for (Map.Entry<Integer, List<TaxInvoiceLedgerShowDTO>> monthEntry : monthlyMap.entrySet()) {
                int month = monthEntry.getKey();
                List<TaxInvoiceLedgerShowDTO> monthList = monthEntry.getValue();

                int monthVoucherCount = monthList.stream().mapToInt(TaxInvoiceLedgerShowDTO::getVoucherCount).sum();
                BigDecimal monthSupplyAmount = monthList.stream()
                        .map(TaxInvoiceLedgerShowDTO::getSupplyAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal monthVatAmount = monthList.stream()
                        .map(TaxInvoiceLedgerShowDTO::getVatAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);


                TaxInvoiceLedgerShowDTO monthSummaryDTO = new TaxInvoiceLedgerShowDTO(
                        groupedList.get(0).getClientCode(),
                        groupedList.get(0).getClientName(),
                        groupedList.get(0).getClientNumber(),
                        month,
                        monthVoucherCount,
                        monthSupplyAmount,
                        monthVatAmount
                );

                summaryDTO.getAllShows().add(monthSummaryDTO);


                monthlyTotalSupply.put(month, monthlyTotalSupply.getOrDefault(month, BigDecimal.ZERO).add(monthSupplyAmount));
                monthlyTotalVat.put(month, monthlyTotalVat.getOrDefault(month, BigDecimal.ZERO).add(monthVatAmount));
                monthlyTotalVoucherCount.put(month, monthlyTotalVoucherCount.getOrDefault(month, 0) + monthVoucherCount);


                grandTotalSupply = grandTotalSupply.add(monthSupplyAmount);
                grandTotalVat = grandTotalVat.add(monthVatAmount);
                grandTotalVoucherCount += monthVoucherCount;
            }
            realFinalList.add(summaryDTO);
        }

        // 정렬기능
        realFinalList.sort(Comparator
                .comparing((TaxInvoiceLedgerShowAllDTO dto2) -> Integer.parseInt(dto2.getClientCode())));

        // 각 월별 총합계 추가

        List<TaxInvoiceLedgerShowDTO> totalList = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> monthEntry : monthlyTotalSupply.entrySet()) {
            int month = monthEntry.getKey();
            TaxInvoiceLedgerShowDTO monthlyTotalDTO = new TaxInvoiceLedgerShowDTO(
                    "합계",
                    "합계",
                    "합계",
                    month,
                    monthlyTotalVoucherCount.get(month),
                    monthlyTotalSupply.get(month),
                    monthlyTotalVat.get(month)
            );
            totalList.add(monthlyTotalDTO);
        }

        // 최종 총합계
        TaxInvoiceLedgerShowAllDTO grandTotalDTO = TaxInvoiceLedgerShowAllDTO.create(
                "합계",
                "합계",
                "합계",
                grandTotalVoucherCount,
                grandTotalSupply,
                grandTotalVat,
                totalList
        );

        realFinalList.add(grandTotalDTO);
        return realFinalList;

}
}
