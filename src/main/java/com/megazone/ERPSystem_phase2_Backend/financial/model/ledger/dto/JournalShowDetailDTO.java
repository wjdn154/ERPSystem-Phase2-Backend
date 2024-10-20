package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalShowDetailDTO {
    private Long id;
    private LocalDate voucherDate;
    private Long voucherNumber;
    private VoucherType voucherType;
    private VoucherKind voucherKind;
    private String accountSubjectCode;
    private String accountSubjectName;
    private String departmentName;
    private String voucherManagerName;
    private String clientCode;
    private String clientName;
    private String clientRegisterNumber;
    private String representativeName;
    private String transactionDescription;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;

    public static JournalShowDetailDTO create(UnresolvedVoucher unresolvedVoucher) {
        return new JournalShowDetailDTO(
                unresolvedVoucher.getId(),
                unresolvedVoucher.getVoucherDate(),
                unresolvedVoucher.getVoucherNumber(),
                unresolvedVoucher.getVoucherType(),
                unresolvedVoucher.getVoucherKind(),
                unresolvedVoucher.getAccountSubject().getCode(),
                unresolvedVoucher.getAccountSubject().getName(),
                unresolvedVoucher.getVoucherManager().getDepartment().getDepartmentName(),
                unresolvedVoucher.getVoucherManager().getLastName() + unresolvedVoucher.getVoucherManager().getFirstName(),
                unresolvedVoucher.getClient().getCode(),
                unresolvedVoucher.getClient().getPrintClientName(),
                unresolvedVoucher.getClient().getBusinessRegistrationNumber(),
                unresolvedVoucher.getClient().getRepresentativeName(),
                unresolvedVoucher.getTransactionDescription(),
                unresolvedVoucher.getDebitAmount(),
                unresolvedVoucher.getCreditAmount()
        );
    }
}
