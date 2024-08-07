package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResolvedVoucherRepository extends JpaRepository<ResolvedVoucher,Long>,ResolvedVoucherRepositoryCustom {
}
