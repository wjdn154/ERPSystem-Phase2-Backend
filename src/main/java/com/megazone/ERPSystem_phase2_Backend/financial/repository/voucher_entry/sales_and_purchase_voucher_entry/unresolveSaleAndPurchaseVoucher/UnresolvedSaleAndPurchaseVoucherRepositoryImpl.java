package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher;


import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QUnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QUnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UnresolvedSaleAndPurchaseVoucherRepositoryImpl implements UnresolvedSaleAndPurchaseVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<UnresolvedSaleAndPurchaseVoucher> findApprovalTypeVoucher(UnresolvedSaleAndPurchaseVoucherApprovalDTO dto,
                                                                          Long companyId) {
        QUnresolvedSaleAndPurchaseVoucher qUnresolvedVoucher = QUnresolvedSaleAndPurchaseVoucher.unresolvedSaleAndPurchaseVoucher;

        List<UnresolvedSaleAndPurchaseVoucher> results = queryFactory.selectFrom(qUnresolvedVoucher)
                .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
                        .and(qUnresolvedVoucher.voucherNumber.in(dto.getSearchVoucherNumList()))
                        .and(qUnresolvedVoucher.company.id.eq(companyId))
                        .and(qUnresolvedVoucher.approvalStatus.eq(ApprovalStatus.PENDING)))

                .fetch();

        return results;
    }
}
