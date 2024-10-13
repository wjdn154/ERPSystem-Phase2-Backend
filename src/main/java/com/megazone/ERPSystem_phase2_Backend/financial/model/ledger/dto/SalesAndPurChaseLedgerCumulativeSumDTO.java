package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesAndPurChaseLedgerCumulativeSumDTO {
    private Integer totalVoucherCount;
    private BigDecimal cumulativeSupplyAmount;
    private BigDecimal cumulativeVatAmount;
    private BigDecimal cumulativeSumAmount;

    public static SalesAndPurChaseLedgerCumulativeSumDTO create(Integer totalVoucherCount, BigDecimal cumulativeSupplyAmount,
                                                                BigDecimal cumulativeVatAmount, BigDecimal cumulativeSumAmount) {
        return new SalesAndPurChaseLedgerCumulativeSumDTO(totalVoucherCount, cumulativeSupplyAmount, cumulativeVatAmount, cumulativeSumAmount);
    }
}
