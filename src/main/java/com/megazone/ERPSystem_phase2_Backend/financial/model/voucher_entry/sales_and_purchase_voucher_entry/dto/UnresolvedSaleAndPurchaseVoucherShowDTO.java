package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnresolvedSaleAndPurchaseVoucherShowDTO {
    private LocalDate voucherDate;
    private Long voucherNumber;
    private String vatTypeCode;
    private String vatTypeName;
    private String itemName;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal supplyAmount;
    private BigDecimal vatAmount;
    private String clientCode;
    private String clientName;
    private String voucherManagerName;
    private ElectronicTaxInvoiceStatus invoiceStatus;
    private String journalEntryName;
    private TransactionType TransactionType;

    public static UnresolvedSaleAndPurchaseVoucherShowDTO create(UnresolvedSaleAndPurchaseVoucher voucher) {
        return new UnresolvedSaleAndPurchaseVoucherShowDTO(
                voucher.getVoucherDate(),
                voucher.getVoucherNumber(),
                voucher.getVatType().getCode(),
                voucher.getVatType().getVatName(),
                voucher.getItemName(),
                voucher.getQuantity(),
                voucher.getUnitPrice(),
                voucher.getSupplyAmount(),
                voucher.getVatAmount(),
                voucher.getClient().getCode(),
                voucher.getClient().getPrintClientName(),
                voucher.getVoucherManager().getFirstName() + voucher.getVoucherManager().getLastName(),
                voucher.getElectronicTaxInvoiceStatus(),
                voucher.getJournalEntry().getName(),
                voucher.getVatType().getTransactionType()
        );
    }
}
