package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UnresolvedSaleAndPurchaseVoucherRepository extends JpaRepository<UnresolvedSaleAndPurchaseVoucher, Long>,UnresolvedSaleAndPurchaseVoucherRepositoryCustom {
    Optional<UnresolvedSaleAndPurchaseVoucher> findFirstByVoucherDateOrderByIdDesc(LocalDate date);

    List<UnresolvedSaleAndPurchaseVoucher> findByVoucherDateOrderByVoucherNumberAsc(LocalDate date);

    UnresolvedSaleAndPurchaseVoucher findByVoucherNumber(Long voucherNumber);

    // 담당자 ID 추가필요.
    void deleteByVoucherNumberAndVoucherDate(Long voucherNumber, LocalDate searchDate);

}
