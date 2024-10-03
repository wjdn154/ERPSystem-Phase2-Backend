package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;

import java.time.LocalDate;
import java.util.List;

public interface UnresolvedVoucherRepositoryCustom {
    List<Long> deleteVoucherByManager(UnresolvedVoucherDeleteDTO dto);
    List<UnresolvedVoucher> findApprovalTypeVoucher(UnresolvedVoucherApprovalDTO dto);
    List<UnresolvedVoucher> ApprovalAllSearch(LocalDate date);
}
