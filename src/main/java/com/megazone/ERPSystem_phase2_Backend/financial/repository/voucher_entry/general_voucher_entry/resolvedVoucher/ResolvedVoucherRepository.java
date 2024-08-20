package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ResolvedVoucherRepository extends JpaRepository<ResolvedVoucher,Long>,ResolvedVoucherRepositoryCustom {
    List<ResolvedVoucher> findByVoucherDateOrderByVoucherNumberAsc(LocalDate date);
}
