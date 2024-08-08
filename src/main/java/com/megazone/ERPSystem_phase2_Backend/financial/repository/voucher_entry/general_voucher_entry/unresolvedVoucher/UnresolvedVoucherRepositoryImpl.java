package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UnresolvedVoucherRepositoryImpl implements UnresolvedVoucherRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
}
