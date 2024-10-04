package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxInvoiceLedgerShowAllDTO {
    private String clientCode;
    private String clientName;
    private String clientNumber;
    private int totalVoucherCount;
    private BigDecimal totalSupplyAmount;
    private BigDecimal totalVatAmount;
    private List<TaxInvoiceLedgerShowDTO> taxInvoiceShowList;
}
