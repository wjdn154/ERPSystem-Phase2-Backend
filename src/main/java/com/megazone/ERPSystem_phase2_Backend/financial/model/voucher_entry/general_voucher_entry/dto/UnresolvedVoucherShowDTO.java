package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto;

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
public class UnresolvedVoucherShowDTO {
    private LocalDate voucherDate;
    private String voucherNumber;
    private VoucherType voucherType;
    private String accountSubjectCode;
    private String accountSubjectName;
    private String clientCode;
    private String clientName;
    private String transactionDescription;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;

    public static UnresolvedVoucherShowDTO create(UnresolvedVoucher unresolvedVoucher) {
        return new UnresolvedVoucherShowDTO(
                unresolvedVoucher.getVoucherDate(),
                unresolvedVoucher.getVoucherNumber(),
                unresolvedVoucher.getVoucherType(),
                unresolvedVoucher.getAccountSubject().getCode(),
                unresolvedVoucher.getAccountSubject().getName(),
                unresolvedVoucher.getClient().getCode(),
                unresolvedVoucher.getClient().getPrintClientName(),
                unresolvedVoucher.getTransactionDescription(),
                unresolvedVoucher.getDebitAmount(),
                unresolvedVoucher.getCreditAmount()
        );
    }
}
