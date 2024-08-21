package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;

import java.util.List;

public interface ResolvedVoucherRepositoryCustom {
    List<Long> deleteVoucherByManager(ResolvedVoucherDeleteDTO dto);
}
