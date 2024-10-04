package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QUnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UnresolvedVoucherRepositoryImpl implements UnresolvedVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Long> deleteVoucherByManager(UnresolvedVoucherDeleteDTO dto) {
        QUnresolvedVoucher qUnresolvedVoucher = QUnresolvedVoucher.unresolvedVoucher;

        List<Long> deletedVoucher = dto.getSearchVoucherNumList().stream()
                .flatMap(voucherNum -> queryFactory.select(qUnresolvedVoucher.id)
                        .from(qUnresolvedVoucher)
                        .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                .and(qUnresolvedVoucher.voucherNumber.eq(voucherNum))
//                                .and(qUnresolvedVoucher.voucherManager.id.eq(managerId)))
                        ).fetch().stream()).toList();

        if(!deletedVoucher.isEmpty()) {
            queryFactory.delete(qUnresolvedVoucher)
                    .where(qUnresolvedVoucher.id.in(deletedVoucher))
                    .execute();
            return deletedVoucher;
        }

        return null;
    }

    @Override
    public List<UnresolvedVoucher> findApprovalTypeVoucher(UnresolvedVoucherApprovalDTO dto) {
        QUnresolvedVoucher qUnresolvedVoucher = QUnresolvedVoucher.unresolvedVoucher;


        List<UnresolvedVoucher> pendingVoucherList = queryFactory.selectFrom(qUnresolvedVoucher)
                .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
                        .and(qUnresolvedVoucher.voucherNumber.in(dto.getSearchVoucherNumberList())
                        .and(qUnresolvedVoucher.approvalStatus.eq(ApprovalStatus.PENDING))))
                .fetch();
        return pendingVoucherList;
    }

    @Override
    public List<UnresolvedVoucher> ApprovalAllSearch(LocalDate date) {
        QUnresolvedVoucher qUnresolvedVoucher = QUnresolvedVoucher.unresolvedVoucher;

        List<UnresolvedVoucher> results = queryFactory.selectFrom(qUnresolvedVoucher)
                .where(qUnresolvedVoucher.voucherDate.eq(date)
                        .and(qUnresolvedVoucher.approvalStatus.ne(ApprovalStatus.APPROVED)
                                .and(qUnresolvedVoucher.voucherKind.eq(VoucherKind.GENERAL))))
                .fetch();
        return results;
    }
}
