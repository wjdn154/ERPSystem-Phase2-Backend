package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesAndPurChaseLedgerCumulativeSumDTO {
    private YearMonth month;
    private Integer voucherCount;
    private BigDecimal sumSupplyAmount;
    private BigDecimal sumVatAmount;
    private BigDecimal sumSumAmount;

    public static SalesAndPurChaseLedgerCumulativeSumDTO create(YearMonth month,Integer totalVoucherCount, BigDecimal cumulativeSupplyAmount,
                                                                BigDecimal cumulativeVatAmount, BigDecimal cumulativeSumAmount) {
        return new SalesAndPurChaseLedgerCumulativeSumDTO(month,totalVoucherCount, cumulativeSupplyAmount, cumulativeVatAmount, cumulativeSumAmount);
    }
}
