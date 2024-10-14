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
public class SalesAndPurChaseLedgerMonthlySumDTO {
    private YearMonth month;
    private Integer voucherCount;
    private BigDecimal sumSupplyAmount;
    private BigDecimal sumVatAmount;
    private BigDecimal sumAmount;

    public static SalesAndPurChaseLedgerMonthlySumDTO create(YearMonth month, Integer voucherCount, BigDecimal sumSupplyAmount,
                                                             BigDecimal sumVatAmount, BigDecimal sumAmount) {
        return new SalesAndPurChaseLedgerMonthlySumDTO(month, voucherCount, sumSupplyAmount, sumVatAmount, sumAmount);
    }
}
