package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesAndPurChaseLedgerHalfYearlySumDTO {
    private Integer halfYear;
    private Integer voucherCount;
    private BigDecimal sumSupplyAmount;
    private BigDecimal sumVatAmount;
    private BigDecimal sumAmount;

    public static SalesAndPurChaseLedgerHalfYearlySumDTO create(Integer halfYear, Integer voucherCount, BigDecimal sumSupplyAmount,
                                                                BigDecimal sumVatAmount, BigDecimal sumAmount) {
        return new SalesAndPurChaseLedgerHalfYearlySumDTO(halfYear, voucherCount, sumSupplyAmount, sumVatAmount, sumAmount);
    }
}
