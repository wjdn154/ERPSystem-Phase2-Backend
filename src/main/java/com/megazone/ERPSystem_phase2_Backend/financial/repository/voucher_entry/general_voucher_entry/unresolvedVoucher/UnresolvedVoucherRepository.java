package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnresolvedVoucherRepository extends JpaRepository<UnresolvedVoucher, Long>, UnresolvedVoucherRepositoryCustom {
}
