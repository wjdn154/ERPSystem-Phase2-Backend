package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherPrintSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private VoucherType voucherType;
    private VoucherKind voucherKind;
    private String startAccountCode;
    private String endAccountCode;
    private String startVoucherNumber;
    private String endVoucherNumber;

}
