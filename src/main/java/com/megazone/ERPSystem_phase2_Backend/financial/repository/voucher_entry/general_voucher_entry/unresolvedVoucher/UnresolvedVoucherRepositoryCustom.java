package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherDeleteDTO;

import java.util.List;

public interface UnresolvedVoucherRepositoryCustom {
    List<Long> deleteVoucherByManager(UnresolvedVoucherDeleteDTO dto);
}
