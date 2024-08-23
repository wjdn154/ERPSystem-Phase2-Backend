package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UnresolvedSaleAndPurchaseVoucherRepository extends JpaRepository<UnresolvedSaleAndPurchaseVoucher, Long>,UnresolvedSaleAndPurchaseVoucherRepositoryCustom {
    Optional<UnresolvedSaleAndPurchaseVoucher> findFirstByVoucherDateOrderByIdDesc(LocalDate date);

    List<UnresolvedSaleAndPurchaseVoucher> findByVoucherDateOrderByVoucherNumberAsc(LocalDate date);

}
