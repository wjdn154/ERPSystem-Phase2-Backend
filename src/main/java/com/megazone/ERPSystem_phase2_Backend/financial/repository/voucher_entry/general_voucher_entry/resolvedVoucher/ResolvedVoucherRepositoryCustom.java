package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.ResolvedVoucherDeleteDTO;

import java.util.Collection;

public interface ResolvedVoucherRepositoryCustom {
    Collection<Long> deleteVoucherByManager(ResolvedVoucherDeleteDTO dto);
}
