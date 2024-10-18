package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResolvedVoucherShowDTO {
    private Long voucherId;
    private LocalDate voucherDate;
    private Long voucherNumber;
    private VoucherType voucherType;
    private String accountSubjectCode;
    private String accountSubjectName;
    private String voucherManagerName;
    private String clientCode;
    private String clientName;
    private String transactionDescription;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private VoucherKind voucherKind;

    public static ResolvedVoucherShowDTO create(ResolvedVoucher resolvedVoucher) {
        return new ResolvedVoucherShowDTO(
                resolvedVoucher.getId(),
                resolvedVoucher.getVoucherDate(),
                resolvedVoucher.getVoucherNumber(),
                resolvedVoucher.getVoucherType(),
                resolvedVoucher.getAccountSubject().getCode(),
                resolvedVoucher.getAccountSubject().getName(),
                resolvedVoucher.getVoucherManager().getFirstName() +
                        resolvedVoucher.getVoucherManager().getLastName(),
                resolvedVoucher.getClient().getCode(),
                resolvedVoucher.getClient().getPrintClientName(),
                resolvedVoucher.getTransactionDescription(),
                resolvedVoucher.getDebitAmount(),
                resolvedVoucher.getCreditAmount(),
                resolvedVoucher.getVoucherKind()
        );
    }
}
