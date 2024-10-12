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

//    @Override
//    public List<SalesAndPurChaseLedgerShowAllDTO> showAll(SalesAndPurChaseLedgerSearchDTO dto) {
//        List<SalesAndPurChaseLedgerShowDTO> dtos =resolvedSaleAndPurchaseVoucherRepository.SalesAndPurChaseLedgerShowList(dto);
//
//        List<SalesAndPurChaseLedgerShowAllDTO> resultList = new ArrayList<>();
//
//        Map<LocalDate, List<SalesAndPurChaseLedgerShowDTO>> map = dtos.stream()
//                .collect(Collectors.groupingBy(SalesAndPurChaseLedgerShowDTO::getVoucherDate, LinkedHashMap::new, Collectors.toList()));
//
//        for(Map.Entry<LocalDate, List<SalesAndPurChaseLedgerShowDTO>> entry : map.entrySet()) {
//            LocalDate date = entry.getKey();
//            List<SalesAndPurChaseLedgerShowDTO> dtoList = entry.getValue();
//
//            // 전표 번호 기준 중복 없이 전표 개수 계산
//            int voucherCount = (int) dtoList.stream()
//                    .map(SalesAndPurChaseLedgerShowDTO::getVoucherNumber)
//                    .distinct()
//                    .count();
//
//            SalesAndPurChaseLedgerDailySumDTO dailySumDTO;
//
////            if(voucherCount <= 1) {
////                dailySumDTO = null;
////            }
////            else {
//
//
//                // 공급가액, 부가세, 총 금액의 합계 계산
//                BigDecimal sumSupplyAmount = dtoList.stream()
//                        .map(SalesAndPurChaseLedgerShowDTO::getSupplyAmount)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//                BigDecimal sumVatAmount = dtoList.stream()
//                        .map(SalesAndPurChaseLedgerShowDTO::getVatAmount)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//                BigDecimal sumAmount = dtoList.stream()
//                        .map(SalesAndPurChaseLedgerShowDTO::getSumAmount)  // 공급가액 + 부가세
//                        .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//                dailySumDTO = SalesAndPurChaseLedgerDailySumDTO.create(voucherCount, sumSupplyAmount, sumVatAmount, sumAmount);
////            }
//
//            SalesAndPurChaseLedgerShowAllDTO result = SalesAndPurChaseLedgerShowAllDTO.create(
//                    dtoList, dailySumDTO
//            );
//
//            resultList.add(result);
//        }
//
//        return resultList;
//    }
@Override
public SalesAndPurChaseLedgerShowAllDTO showAll(SalesAndPurChaseLedgerSearchDTO dto) {
    List<SalesAndPurChaseLedgerShowDTO> dtos = resolvedSaleAndPurchaseVoucherRepository.SalesAndPurChaseLedgerShowList(dto);

    // 각 계별 결과를 저장할 리스트
    List<SalesAndPurChaseLedgerDailySumDTO> dailySummaries = new ArrayList<>();
    List<SalesAndPurChaseLedgerMonthlySumDTO> monthlySummaries = new ArrayList<>();
    List<SalesAndPurChaseLedgerQuarterlySumDTO> quarterlySummaries = new ArrayList<>();
    List<SalesAndPurChaseLedgerHalfYearlySumDTO> halfYearlySummaries = new ArrayList<>();
    List<SalesAndPurChaseLedgerCumulativeSumDTO> cumulativeSummaries = new ArrayList<>(); // 월별 누계 저장용

    // 누계 계산용 변수
    BigDecimal cumulativeSupply = BigDecimal.ZERO;
    BigDecimal cumulativeVat = BigDecimal.ZERO;
    BigDecimal cumulativeSum = BigDecimal.ZERO;

    // 그룹화 및 일계, 월계, 분기계, 반기계, 누계 집계 처리
    Map<YearMonth, BigDecimal> monthlySupplyTotals = new HashMap<>();
    Map<Integer, BigDecimal> quarterlySupplyTotals = new HashMap<>();
    Map<Integer, BigDecimal> halfYearlySupplyTotals = new HashMap<>();

    for (SalesAndPurChaseLedgerShowDTO dtoItem : dtos) {
        LocalDate voucherDate = dtoItem.getVoucherDate();
        YearMonth yearMonth = YearMonth.from(voucherDate);
        int quarter = (voucherDate.getMonthValue() - 1) / 3 + 1;
        int halfYear = (voucherDate.getMonthValue() - 1) / 6 + 1;

        // 일계 계산
        BigDecimal supplyAmount = dtoItem.getSupplyAmount();
        BigDecimal vatAmount = dtoItem.getVatAmount();
        BigDecimal sumAmount = dtoItem.getSumAmount();

        dailySummaries.add(SalesAndPurChaseLedgerDailySumDTO.create(1, supplyAmount, vatAmount, sumAmount));

        // 월계 계산
        monthlySupplyTotals.put(yearMonth, monthlySupplyTotals.getOrDefault(yearMonth, BigDecimal.ZERO).add(supplyAmount));
        monthlySummaries.add(SalesAndPurChaseLedgerMonthlySumDTO.create(yearMonth, 1, supplyAmount, vatAmount, sumAmount));

        // 분기계 계산
        quarterlySupplyTotals.put(quarter, quarterlySupplyTotals.getOrDefault(quarter, BigDecimal.ZERO).add(supplyAmount));
        quarterlySummaries.add(SalesAndPurChaseLedgerQuarterlySumDTO.create(quarter, 1, supplyAmount, vatAmount, sumAmount));

        // 반기계 계산
        halfYearlySupplyTotals.put(halfYear, halfYearlySupplyTotals.getOrDefault(halfYear, BigDecimal.ZERO).add(supplyAmount));
        halfYearlySummaries.add(SalesAndPurChaseLedgerHalfYearlySumDTO.create(halfYear, 1, supplyAmount, vatAmount, sumAmount));

        // 누계 계산
        cumulativeSupply = cumulativeSupply.add(supplyAmount);
        cumulativeVat = cumulativeVat.add(vatAmount);
        cumulativeSum = cumulativeSum.add(sumAmount);
        cumulativeSummaries.add(SalesAndPurChaseLedgerCumulativeSumDTO.create(1, cumulativeSupply, cumulativeVat, cumulativeSum));
    }

    // 최종 결과 반환
    return SalesAndPurChaseLedgerShowAllDTO.create(
            dtos, dailySummaries, monthlySummaries, quarterlySummaries, halfYearlySummaries, cumulativeSummaries
    );
}
}

