package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherDeleteDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QUnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UnresolvedVoucherRepositoryImpl implements UnresolvedVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Long> deleteVoucherByManager(UnresolvedVoucherDeleteDto dto) {
        QUnresolvedVoucher qUnresolvedVoucher = QUnresolvedVoucher.unresolvedVoucher;

        List<Long> deletedVoucher = dto.getSearchVoucherNumList().stream()
                .flatMap(voucherNum -> queryFactory.select(qUnresolvedVoucher.id)
                        .from(qUnresolvedVoucher)
                        .where(qUnresolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                .and(qUnresolvedVoucher.voucherNumber.eq(voucherNum))
//                                .and(qUnresolvedVoucher.voucherManager.id.eq(managerId)))
                        ).fetch().stream())
                .collect(Collectors.toList());

        if(!deletedVoucher.isEmpty()) {
            queryFactory.delete(qUnresolvedVoucher)
                    .where(qUnresolvedVoucher.id.in(deletedVoucher))
                    .execute();
            return deletedVoucher;
        }

        return null;
    }
}
