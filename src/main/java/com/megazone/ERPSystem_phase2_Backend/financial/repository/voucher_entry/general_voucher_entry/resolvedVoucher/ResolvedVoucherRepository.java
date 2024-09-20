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
    List<ResolvedVoucher> findByVoucherDateOrderByVoucherNumberAsc(LocalDate date);

//    @Query("SELECT r FROM resolved_voucher r WHERE r.voucherDate between :startDate AND :endDate " +
//            "AND r.accountSubject.code >= :startAccountCode AND r.accountSubject.code <= :endAccountCode")
//    List<ResolvedVoucher> generalSearch(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate,
//                                        @Param("startAccountCode") String startAccountCode, @Param("endAccountCode") String endAccountCode);

//    @Query(value = "SELECT * FROM resolved_voucher r WHERE r.voucher_date BETWEEN :startDate AND :endDate " +
//            "AND CAST(r.account_subject_code AS UNSIGNED) >= CAST(:startAccountCode AS UNSIGNED) " +
//            "AND CAST(r.account_subject_code AS UNSIGNED) <= CAST(:endAccountCode AS UNSIGNED)",
//            nativeQuery = true)
//    List<ResolvedVoucher> generalSearch(@Param("startDate") LocalDate startDate,
//                                        @Param("endDate") LocalDate endDate,
//                                        @Param("startAccountCode") String startAccountCode,
//                                        @Param("endAccountCode") String endAccountCode);

    @Query("SELECT sum(r.debitAmount) FROM resolved_voucher r WHERE r.voucherDate between :startDate AND :endDate")
    BigDecimal generalTotalDebit(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

    @Query("SELECT sum(r.creditAmount) FROM resolved_voucher r WHERE r.voucherDate between :startDate AND :endDate")
    BigDecimal generalTotalCredit(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

    @Query(value = "SELECT COUNT(*) FROM ("
            + "SELECT 1 "
            + "FROM resolved_voucher "
            + "WHERE voucher_date BETWEEN :startDate AND :endDate "
            + "GROUP BY voucher_date, voucher_number) AS grouped_vouchers",
            nativeQuery = true)
    BigDecimal generalTotalCount(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
}
