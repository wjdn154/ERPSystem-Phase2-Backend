package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QUnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UnresolvedVoucherRepositoryImpl implements UnresolvedVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Long> deleteVoucherByManager(Long companyId, UnresolvedVoucherDeleteDTO dto) {
        QUnresolvedVoucher qUnresolvedVoucher = QUnresolvedVoucher.unresolvedVoucher;

        List<Long> deletedVoucher = dto.getSearchVoucherNumList().stream()
                .flatMap(voucherNum -> queryFactory.select(qUnresolvedVoucher.id)
                        .from(qUnresolvedVoucher)
                        .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                .and(qUnresolvedVoucher.voucherNumber.eq(voucherNum))
                                        .and(qUnresolvedVoucher.company.id.eq(companyId))
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
    public List<UnresolvedVoucher> findApprovalTypeVoucher(Long companyId, UnresolvedVoucherApprovalDTO dto) {
        QUnresolvedVoucher qUnresolvedVoucher = QUnresolvedVoucher.unresolvedVoucher;

        List<UnresolvedVoucher> pendingVoucherList = dto.getSearchVoucherNumList().stream()
                .flatMap(voucherNum -> queryFactory.selectFrom(qUnresolvedVoucher)
                        .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                .and(qUnresolvedVoucher.voucherNumber.eq(voucherNum))
                                .and(qUnresolvedVoucher.company.id.eq(companyId))
                                .and(qUnresolvedVoucher.approvalStatus.eq(ApprovalStatus.PENDING)))
                        .fetch().stream())
                .collect(Collectors.toList());
        return pendingVoucherList;
    }
}
