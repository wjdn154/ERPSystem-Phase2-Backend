package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnresolvedVoucherShowDto {
    private LocalDate voucherDate;
    private String voucherNumber;
    private VoucherType voucherType;
    private String accountSubjectCode;
    private String accountSubjectName;
//    private String vendorCode;
//    private String vendorName;
//    private String descriptionCode;
//    private String descriptionName;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;

    public static UnresolvedVoucherShowDto create(UnresolvedVoucher unresolvedVoucher) {
        return new UnresolvedVoucherShowDto(
                unresolvedVoucher.getVoucherDate(),
                unresolvedVoucher.getVoucherNumber(),
                unresolvedVoucher.getVoucherType(),
                unresolvedVoucher.getAccountSubject().getCode(),
                unresolvedVoucher.getAccountSubject().getName(),
//                unresolvedVoucher.getVendor().getCode(),
//                unresolvedVoucher.getVendor().getName(),
//                unresolvedVoucher.getDescription().getCode(),
//                unresolvedVoucher.getDescription().getName(),
                unresolvedVoucher.getDebitAmount(),
                unresolvedVoucher.getCreditAmount()
        );
    }
}
