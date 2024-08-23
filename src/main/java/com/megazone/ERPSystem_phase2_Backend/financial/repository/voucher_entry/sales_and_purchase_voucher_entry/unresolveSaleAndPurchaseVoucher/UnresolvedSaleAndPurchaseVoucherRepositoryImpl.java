package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher;


import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QUnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UnresolvedSaleAndPurchaseVoucherRepositoryImpl implements UnresolvedSaleAndPurchaseVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> deleteVoucherByManager(UnresolvedSaleAndPurchaseVoucherDeleteDTO dto) {
        QUnresolvedSaleAndPurchaseVoucher qUnresolvedSaleAndPurchaseVoucher = QUnresolvedSaleAndPurchaseVoucher.unresolvedSaleAndPurchaseVoucher;

//        List<Long> deletedVoucher = dto.getSearchVoucherNumList().stream()
//                .flatMap(voucherNum -> queryFactory.select(qUnresolvedVoucher.id)
//                        .from(qUnresolvedVoucher)
//                        .fetchJoin()
//                        .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
//                                        .and(qUnresolvedVoucher.voucherNumber.eq(voucherNum))
////                                .and(qUnresolvedVoucher.voucherManager.id.eq(managerId)))
//                        ).fetch().stream()).toList();
//
//        if(!deletedVoucher.isEmpty()) {
//            queryFactory.delete(qUnresolvedVoucher)
//                    .where(qUnresolvedVoucher.id.in(deletedVoucher))
//                    .execute();
//            return deletedVoucher;
//        }
//
//        return null;
        queryFactory.delete(qUnresolvedSaleAndPurchaseVoucher)
                .where(qUnresolvedSaleAndPurchaseVoucher.id.eq(1L))
                .execute();
        return null;
    }


}
