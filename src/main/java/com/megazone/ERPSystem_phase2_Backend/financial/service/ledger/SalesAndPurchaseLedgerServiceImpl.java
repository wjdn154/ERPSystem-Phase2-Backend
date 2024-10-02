package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerDailySumDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher.ResolvedSaleAndPurchaseVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesAndPurchaseLedgerServiceImpl implements SalesAndPurchaseLedgerService {
    private final ResolvedSaleAndPurchaseVoucherRepository resolvedSaleAndPurchaseVoucherRepository;

    @Override
    public List<SalesAndPurChaseLedgerShowAllDTO> showAll(SalesAndPurChaseLedgerSearchDTO dto) {
        List<SalesAndPurChaseLedgerShowDTO> dtos =resolvedSaleAndPurchaseVoucherRepository.SalesAndPurChaseLedgerShowList(dto);

        List<SalesAndPurChaseLedgerShowAllDTO> resultList = new ArrayList<>();

        Map<LocalDate, List<SalesAndPurChaseLedgerShowDTO>> map = dtos.stream()
                .collect(Collectors.groupingBy(SalesAndPurChaseLedgerShowDTO::getVoucherDate, LinkedHashMap::new, Collectors.toList()));

        for(Map.Entry<LocalDate, List<SalesAndPurChaseLedgerShowDTO>> entry : map.entrySet()) {
            LocalDate date = entry.getKey();
            List<SalesAndPurChaseLedgerShowDTO> dtoList = entry.getValue();

            // 전표 번호 기준 중복 없이 전표 개수 계산
            int voucherCount = (int) dtoList.stream()
                    .map(SalesAndPurChaseLedgerShowDTO::getVoucherNumber)  // 전표 번호 추출
                    .distinct()  // 중복 제거
                    .count();  // 고유 전표 번호 개수

            SalesAndPurChaseLedgerDailySumDTO dailySumDTO;

            if(voucherCount <= 1) {
                dailySumDTO = null;
            }
            else {
                // 공급가액, 부가세, 총 금액의 합계 계산
                BigDecimal sumSupplyAmount = dtoList.stream()
                        .map(SalesAndPurChaseLedgerShowDTO::getSupplyAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal sumVatAmount = dtoList.stream()
                        .map(SalesAndPurChaseLedgerShowDTO::getVatAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal sumAmount = dtoList.stream()
                        .map(SalesAndPurChaseLedgerShowDTO::getSumAmount)  // 공급가액 + 부가세
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                dailySumDTO = SalesAndPurChaseLedgerDailySumDTO.create(voucherCount, sumSupplyAmount, sumVatAmount, sumAmount);
            }

            // SalesAndPurChaseLedgerShowAllDTO 객체 생성
            SalesAndPurChaseLedgerShowAllDTO result = SalesAndPurChaseLedgerShowAllDTO.create(
                    dtoList, dailySumDTO
            );

            // 결과를 resultMap에 저장
            resultList.add(result);
        }

        return resultList;
    }
}
