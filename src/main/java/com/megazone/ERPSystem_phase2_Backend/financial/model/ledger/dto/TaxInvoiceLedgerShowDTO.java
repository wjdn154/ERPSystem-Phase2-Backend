package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxInvoiceLedgerShowDTO {
    private Integer month;
    private int voucherCount;
    private BigDecimal supplyAmount;
    private BigDecimal vatAmount;

    public int getMonth() {
        return month;
    }
}
