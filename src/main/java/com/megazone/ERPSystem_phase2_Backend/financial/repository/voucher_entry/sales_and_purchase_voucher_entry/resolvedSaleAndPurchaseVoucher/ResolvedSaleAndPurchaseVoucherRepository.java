package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ResolvedSaleAndPurchaseVoucherRepository extends JpaRepository<ResolvedSaleAndPurchaseVoucher, Long> {
    List<ResolvedSaleAndPurchaseVoucher> findByVoucherDateAndCompany_idOrderByVoucherNumberAsc(LocalDate date,Long companyId);

    ResolvedSaleAndPurchaseVoucher findByVoucherNumberAndCompany_id(String voucherNumber,Long companyId);

    // 승인권자 Id 필요
    void deleteByVoucherNumberAndVoucherDateAndCompany_id(String voucherNumber, LocalDate searchDate,Long companyId);
}
