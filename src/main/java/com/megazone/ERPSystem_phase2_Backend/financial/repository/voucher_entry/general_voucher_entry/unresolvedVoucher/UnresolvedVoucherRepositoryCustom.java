package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;

import java.util.List;

public interface UnresolvedVoucherRepositoryCustom {
    List<Long> deleteVoucherByManager(Long companyId, UnresolvedVoucherDeleteDTO dto);
    List<UnresolvedVoucher> findApprovalTypeVoucher(Long companyId, UnresolvedVoucherApprovalDTO dto);
}
