package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import org.apache.catalina.Manager;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UnresolvedVoucherRepository extends JpaRepository<UnresolvedVoucher, Long>, UnresolvedVoucherRepositoryCustom {
    Optional<UnresolvedVoucher> findFirstByVoucherDateAndCompany_idOrderByIdDesc(LocalDate voucherDate, Long companyId);
    List<UnresolvedVoucher> findByVoucherDateAndCompany_idOrderByVoucherNumberAsc(LocalDate date, Long companyId);
}
