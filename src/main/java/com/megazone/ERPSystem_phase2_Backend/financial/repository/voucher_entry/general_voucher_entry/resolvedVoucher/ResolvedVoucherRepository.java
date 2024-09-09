package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ResolvedVoucherRepository extends JpaRepository<ResolvedVoucher,Long>,ResolvedVoucherRepositoryCustom {
    List<ResolvedVoucher> findByVoucherDateAndCompany_IdOrderByVoucherNumberAsc(LocalDate date,Long companyId);

    @Query("SELECT r FROM resolved_voucher r WHERE r.voucherDate between :startDate AND :endDate")
    List<ResolvedVoucher> journalSearch(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

    @Query("SELECT sum(r.debitAmount) FROM resolved_voucher r WHERE r.voucherDate between :startDate AND :endDate")
    BigDecimal testTotalDebit(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

    @Query("SELECT sum(r.creditAmount) FROM resolved_voucher r WHERE r.voucherDate between :startDate AND :endDate")
    BigDecimal testTotalCredit(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

    @Query(value = "SELECT COUNT(*) FROM ("
            + "SELECT 1 "
            + "FROM resolved_voucher "
            + "WHERE voucher_date BETWEEN :startDate AND :endDate "
            + "GROUP BY voucher_date, voucher_number) AS grouped_vouchers",
            nativeQuery = true)
    BigDecimal journalTotalCount(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
}
