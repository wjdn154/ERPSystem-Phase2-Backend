package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesAndPurChaseLedgerDailySumDTO {
    private Integer voucherCount;
    private BigDecimal sumSupplyAmount;
    private BigDecimal sumVatAmount;
    private BigDecimal sumAmount;

    public static SalesAndPurChaseLedgerDailySumDTO create(Integer voucherCount, BigDecimal sumSupplyAmount,
                                                           BigDecimal sumVatAmount, BigDecimal sumAmount) {
        return new SalesAndPurChaseLedgerDailySumDTO(
                voucherCount, sumSupplyAmount, sumVatAmount, sumAmount);
    }

}
