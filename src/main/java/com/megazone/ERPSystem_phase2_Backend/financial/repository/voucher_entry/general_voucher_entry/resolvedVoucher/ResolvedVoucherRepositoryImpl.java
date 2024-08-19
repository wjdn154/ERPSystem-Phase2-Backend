package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QUnresolvedVoucher;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ResolvedVoucherRepositoryImpl implements ResolvedVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> deleteVoucherByManager(ResolvedVoucherDeleteDTO dto) {
        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;

        List<Long> deletedVoucher = dto.getSearchVoucherNumList().stream()
                .flatMap(voucherNum -> queryFactory.select(qResolvedVoucher.id)
                        .from(qResolvedVoucher)
                        .where(qResolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                        .and(qResolvedVoucher.voucherNumber.eq(voucherNum))
//                                .and(qUnresolvedVoucher.voucherManager.id.eq(managerId)))
                        ).fetch().stream())
                .collect(Collectors.toList());

        if(!deletedVoucher.isEmpty()) {
            queryFactory.delete(qResolvedVoucher)
                    .where(qResolvedVoucher.id.in(deletedVoucher))
                    .execute();
            return deletedVoucher;
        }
        return null;
    }
}
