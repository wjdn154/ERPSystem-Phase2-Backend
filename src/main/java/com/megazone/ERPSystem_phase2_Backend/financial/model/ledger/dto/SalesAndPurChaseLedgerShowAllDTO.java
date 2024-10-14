package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesAndPurChaseLedgerShowAllDTO {
    private List<SalesAndPurChaseLedgerShowDTO> salesAndPurChaseLedgerShowList; // 전표 리스트
    private List<SalesAndPurChaseLedgerDailySumDTO> dailySummaries; // 일계 요약 리스트
    private List<SalesAndPurChaseLedgerMonthlySumDTO> monthlySummaries; // 월계 요약 리스트
    private List<SalesAndPurChaseLedgerQuarterlySumDTO> quarterlySummaries; // 분기계 요약 리스트
    private List<SalesAndPurChaseLedgerHalfYearlySumDTO> halfYearlySummaries; // 반기계 요약 리스트
    private List<SalesAndPurChaseLedgerCumulativeSumDTO> cumulativeSummaries; // 누계 요약 리스트

    public static SalesAndPurChaseLedgerShowAllDTO create(
            List<SalesAndPurChaseLedgerShowDTO> salesAndPurChaseLedgerShowList,
            List<SalesAndPurChaseLedgerDailySumDTO> dailySummaries,
            List<SalesAndPurChaseLedgerMonthlySumDTO> monthlySummaries,
            List<SalesAndPurChaseLedgerQuarterlySumDTO> quarterlySummaries,
            List<SalesAndPurChaseLedgerHalfYearlySumDTO> halfYearlySummaries,
            List<SalesAndPurChaseLedgerCumulativeSumDTO> cumulativeSummaries
    ) {
        return new SalesAndPurChaseLedgerShowAllDTO(
                salesAndPurChaseLedgerShowList,
                dailySummaries,
                monthlySummaries,
                quarterlySummaries,
                halfYearlySummaries,
                cumulativeSummaries
        );
    }
}
