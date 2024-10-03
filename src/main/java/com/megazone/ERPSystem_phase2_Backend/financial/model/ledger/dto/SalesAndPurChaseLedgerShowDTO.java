package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesAndPurChaseLedgerShowDTO {
    private Long voucherId;
    private String vatTypeName;
    private LocalDate voucherDate;
    private String voucherNumber;
    private String itemName;
    private BigDecimal supplyAmount;
    private BigDecimal vatAmount;
    private BigDecimal sumAmount;
    private String clientCode;
    private String clientName;
    private ElectronicTaxInvoiceStatus electronicTaxInvoiceStatus;
    private String journalEntryName;
//    private String creditCardCode;
//    private String bankAccountName;
//    private String bankAccountCode;
    private String voucherManagerCode;
    private String voucherManagerDepartmentName;
    private String voucherManagerName;

    public static SalesAndPurChaseLedgerShowDTO create(Long voucherId, String vatTypeName, LocalDate voucherDate, String voucherNumber, String itemName,
                                         BigDecimal supplyAmount, BigDecimal vatAmount, BigDecimal sumAmount, String clientCode,
                                         String clientName, ElectronicTaxInvoiceStatus electronicTaxInvoiceStatus,
                                         String journalEntryName, /*String creditCardCode, String bankAccountName,
                                         String bankAccountCode,*/ String voucherManagerCode, String voucherManagerDepartmentName,
                                         String voucherManagerName) {
        return new SalesAndPurChaseLedgerShowDTO(
                voucherId,
                vatTypeName,
                voucherDate,
                voucherNumber,
                itemName,
                supplyAmount,
                vatAmount,
                sumAmount,
                clientCode,
                clientName,
                electronicTaxInvoiceStatus,
                journalEntryName,
//                creditCardCode,
//                bankAccountName,
//                bankAccountCode,
                voucherManagerCode,
                voucherManagerDepartmentName,
                voucherManagerName
        );
    }
}
