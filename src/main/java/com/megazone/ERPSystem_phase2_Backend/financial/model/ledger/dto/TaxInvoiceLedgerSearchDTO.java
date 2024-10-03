package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxInvoiceLedgerSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private TransactionType transactionType;
    private String startClientCode;
    private String endClientCode;
}
