package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import org.apache.catalina.Manager;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UnresolvedVoucherRepository extends JpaRepository<UnresolvedVoucher, Long>, UnresolvedVoucherRepositoryCustom {
    Optional<UnresolvedVoucher> findFirstByVoucherDateAndCompany_idOrderByIdDesc(LocalDate voucherDate, Long companyId);
    List<UnresolvedVoucher> findByVoucherDateAndCompany_idOrderByVoucherNumberAsc(LocalDate date, Long companyId);

    @Query("SELECT r FROM unresolved_voucher r WHERE r.voucherDate between :startDate AND :endDate AND r.company.id = :companyId")
    List<UnresolvedVoucher> journalSearch(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("companyId")Long companyId);

    @Query("SELECT sum(r.debitAmount) FROM unresolved_voucher r WHERE r.voucherDate between :startDate AND :endDate AND r.company.id = :companyId")
    BigDecimal testTotalDebit(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("companyId")Long companyId);

    @Query("SELECT sum(r.creditAmount) FROM unresolved_voucher r WHERE r.voucherDate between :startDate AND :endDate AND r.company.id = :companyId")
    BigDecimal testTotalCredit(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("companyId")Long companyId);

    @Query(value = "SELECT COUNT(*) FROM ("
            + "SELECT 1 "
            + "FROM unresolved_voucher "
            + "WHERE voucher_date BETWEEN :startDate AND :endDate "
            + "AND company_id = :companyId "
            + "GROUP BY voucher_date, voucher_number) AS grouped_vouchers",
            nativeQuery = true)
    BigDecimal journalTotalCount(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("companyId") Long companyId);
}
