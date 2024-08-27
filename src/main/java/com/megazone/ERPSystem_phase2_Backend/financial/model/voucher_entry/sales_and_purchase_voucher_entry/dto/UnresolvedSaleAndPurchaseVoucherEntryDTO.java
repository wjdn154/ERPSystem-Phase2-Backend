package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnresolvedSaleAndPurchaseVoucherEntryDTO {
    private String vatTypeCode;
//    private Vendor vendor;
    private String journalEntryCode;
//    private Long voucherManager;
    private LocalDate voucherDate;
    private String itemName;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private ElectronicTaxInvoiceStatus electronicTaxInvoiceStatus;
}
