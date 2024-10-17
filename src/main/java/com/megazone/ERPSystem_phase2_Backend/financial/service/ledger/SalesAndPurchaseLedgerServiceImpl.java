package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher.ResolvedSaleAndPurchaseVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.time.YearMonth;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesAndPurchaseLedgerServiceImpl implements SalesAndPurchaseLedgerService {
    private final ResolvedSaleAndPurchaseVoucherRepository resolvedSaleAndPurchaseVoucherRepository;
    @Override
    public SalesAndPurChaseLedgerShowAllDTO showAll(SalesAndPurChaseLedgerSearchDTO dto) {
        List<SalesAndPurChaseLedgerShowDTO> dtos = resolvedSaleAndPurchaseVoucherRepository.SalesAndPurChaseLedgerShowList(dto);

        // 각 계별 결과를 저장할 리스트
        List<SalesAndPurChaseLedgerDailySumDTO> dailySummaries = new ArrayList<>();
        List<SalesAndPurChaseLedgerMonthlySumDTO> monthlySummaries = new ArrayList<>();
        List<SalesAndPurChaseLedgerQuarterlySumDTO> quarterlySummaries = new ArrayList<>();
        List<SalesAndPurChaseLedgerHalfYearlySumDTO> halfYearlySummaries = new ArrayList<>();
        List<SalesAndPurChaseLedgerCumulativeSumDTO> cumulativeSummaries = new ArrayList<>(); // 월별 누계 저장용

        // 일계 계산
        Map<LocalDate, List<SalesAndPurChaseLedgerShowDTO>> dailyMap = dtos.stream()
                .collect(Collectors.groupingBy(SalesAndPurChaseLedgerShowDTO::getVoucherDate, LinkedHashMap::new, Collectors.toList()));

        int cumulativeVoucherCount = 0;
        BigDecimal cumulativeSupply = BigDecimal.ZERO;
        BigDecimal cumulativeVat = BigDecimal.ZERO;
        BigDecimal cumulativeSum = BigDecimal.ZERO;

        for (Map.Entry<LocalDate, List<SalesAndPurChaseLedgerShowDTO>> entry : dailyMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<SalesAndPurChaseLedgerShowDTO> dtoList = entry.getValue();

            BigDecimal dailySupplyAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSupplyAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal dailyVatAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getVatAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal dailySumAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSumAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int dailyVoucherCount = (int) dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getVoucherNumber)
                    .distinct()
                    .count();

            // 일계 결과 추가
            dailySummaries.add(SalesAndPurChaseLedgerDailySumDTO.create(dailyVoucherCount, dailySupplyAmount, dailyVatAmount, dailySumAmount));
        }

        // 월계 계산과 함께 누계(월별 누계) 계산
        Map<YearMonth, List<SalesAndPurChaseLedgerShowDTO>> monthlyMap = dtos.stream()
                .collect(Collectors.groupingBy(dtoItem -> YearMonth.from(dtoItem.getVoucherDate()), LinkedHashMap::new, Collectors.toList()));

        for (Map.Entry<YearMonth, List<SalesAndPurChaseLedgerShowDTO>> entry : monthlyMap.entrySet()) {
            YearMonth month = entry.getKey();
            List<SalesAndPurChaseLedgerShowDTO> dtoList = entry.getValue();

            BigDecimal monthlySupplyAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSupplyAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal monthlyVatAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getVatAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal monthlySumAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSumAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int monthlyVoucherCount = dtoList.size();

            // 월계 결과 추가
            monthlySummaries.add(SalesAndPurChaseLedgerMonthlySumDTO.create(month, monthlyVoucherCount, monthlySupplyAmount, monthlyVatAmount, monthlySumAmount));

            // 누계 업데이트: 월별로 누적
            cumulativeVoucherCount = cumulativeVoucherCount + monthlyVoucherCount;
            cumulativeSupply = cumulativeSupply.add(monthlySupplyAmount);
            cumulativeVat = cumulativeVat.add(monthlyVatAmount);
            cumulativeSum = cumulativeSum.add(monthlySumAmount);

            // 누계 결과 추가 (월별 누계)
            cumulativeSummaries.add(SalesAndPurChaseLedgerCumulativeSumDTO.create(month,cumulativeVoucherCount, cumulativeSupply, cumulativeVat, cumulativeSum));
        }

        // 분기계 계산
        Map<Integer, List<SalesAndPurChaseLedgerShowDTO>> quarterlyMap = dtos.stream()
                .collect(Collectors.groupingBy(dtoItem -> (dtoItem.getVoucherDate().getMonthValue() - 1) / 3 + 1, LinkedHashMap::new, Collectors.toList()));

        for (Map.Entry<Integer, List<SalesAndPurChaseLedgerShowDTO>> entry : quarterlyMap.entrySet()) {
            int quarter = entry.getKey();
            List<SalesAndPurChaseLedgerShowDTO> dtoList = entry.getValue();

            BigDecimal quarterlySupplyAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSupplyAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal quarterlyVatAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getVatAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal quarterlySumAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSumAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int quarterlyVoucherCount = dtoList.size();

            // 분기계 결과 추가
            quarterlySummaries.add(SalesAndPurChaseLedgerQuarterlySumDTO.create(quarter, quarterlyVoucherCount, quarterlySupplyAmount, quarterlyVatAmount, quarterlySumAmount));
        }

        // 반기계 계산
        Map<Integer, List<SalesAndPurChaseLedgerShowDTO>> halfYearlyMap = dtos.stream()
                .collect(Collectors.groupingBy(dtoItem -> (dtoItem.getVoucherDate().getMonthValue() - 1) / 6 + 1, LinkedHashMap::new, Collectors.toList()));

        for (Map.Entry<Integer, List<SalesAndPurChaseLedgerShowDTO>> entry : halfYearlyMap.entrySet()) {
            int halfYear = entry.getKey();
            List<SalesAndPurChaseLedgerShowDTO> dtoList = entry.getValue();

            BigDecimal halfYearlySupplyAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSupplyAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal halfYearlyVatAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getVatAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal halfYearlySumAmount = dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getSumAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int halfYearlyVoucherCount = dtoList.size();

            // 반기계 결과 추가
            halfYearlySummaries.add(SalesAndPurChaseLedgerHalfYearlySumDTO.create(halfYear, halfYearlyVoucherCount, halfYearlySupplyAmount, halfYearlyVatAmount, halfYearlySumAmount));
        }

        // 최종 결과 반환
        return SalesAndPurChaseLedgerShowAllDTO.create(
                dtos, dailySummaries, monthlySummaries, quarterlySummaries, halfYearlySummaries, cumulativeSummaries
        );
    }
}

