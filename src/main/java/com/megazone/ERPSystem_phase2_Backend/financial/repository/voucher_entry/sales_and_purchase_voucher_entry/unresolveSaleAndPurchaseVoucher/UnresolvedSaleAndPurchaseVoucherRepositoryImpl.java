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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UnresolvedSaleAndPurchaseVoucherRepositoryImpl implements UnresolvedSaleAndPurchaseVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    /**
     *  미결 매출매입 전표 승인기능
     *  승인대기, 반려, 승인 중 승인상태가 아닌 전표에대한 승인가능 로직
     */

    @Override
    public List<UnresolvedSaleAndPurchaseVoucher> findApprovalTypeVoucher(UnresolvedSaleAndPurchaseVoucherApprovalDTO dto) {
        QUnresolvedSaleAndPurchaseVoucher qUnresolvedVoucher = QUnresolvedSaleAndPurchaseVoucher.unresolvedSaleAndPurchaseVoucher;

        List<UnresolvedSaleAndPurchaseVoucher> results = queryFactory.selectFrom(qUnresolvedVoucher)
                .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                .and(qUnresolvedVoucher.voucherNumber.in(dto.getSearchVoucherNumberList()))
                        .and(qUnresolvedVoucher.approvalStatus.ne(ApprovalStatus.APPROVED)))
                .fetch();

        return results;
    }

    /**
     * 미결 매출매입 전표 승인 조회기능
     */

    @Override
    public List<UnresolvedSaleAndPurchaseVoucher> ApprovalAllSearch(LocalDate voucherDate) {
        QUnresolvedSaleAndPurchaseVoucher qUnresolvedVoucher = QUnresolvedSaleAndPurchaseVoucher.unresolvedSaleAndPurchaseVoucher;

        List<UnresolvedSaleAndPurchaseVoucher> results = queryFactory.selectFrom(qUnresolvedVoucher)
                .where(qUnresolvedVoucher.voucherDate.eq(voucherDate)
                        .and(qUnresolvedVoucher.approvalStatus.eq(ApprovalStatus.PENDING)))
                .fetch();
        return results;
    }
}
