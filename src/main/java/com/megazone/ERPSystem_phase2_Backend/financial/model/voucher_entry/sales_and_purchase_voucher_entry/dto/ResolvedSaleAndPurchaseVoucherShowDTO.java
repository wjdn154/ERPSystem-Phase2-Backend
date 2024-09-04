package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResolvedSaleAndPurchaseVoucherShowDTO {
    private LocalDate voucherDate;
    private String voucherNumber;
    private String vatTypeName;
    private String itemName;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal supplyAmount;
    private BigDecimal vatAmount;
//    private String vendorCode;
//    private String vendorName;
    private ElectronicTaxInvoiceStatus invoiceStatus;
    private String journalEntryName;

    public static ResolvedSaleAndPurchaseVoucherShowDTO create(ResolvedSaleAndPurchaseVoucher voucher) {
        return new ResolvedSaleAndPurchaseVoucherShowDTO(
                voucher.getVoucherDate(),
                voucher.getVoucherNumber(),
                voucher.getVatType().getVatName(),
                voucher.getItemName(),
                voucher.getQuantity(),
                voucher.getUnitPrice(),
                voucher.getSupplyAmount(),
                voucher.getVatAmount(),
//                vendorCode,
//                vendorName,
                voucher.getElectronicTaxInvoiceStatus(),
                voucher.getJournalEntry().getName()
        );
    }
}