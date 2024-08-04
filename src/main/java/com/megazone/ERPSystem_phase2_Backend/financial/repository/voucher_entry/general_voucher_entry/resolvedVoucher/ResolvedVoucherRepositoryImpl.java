package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResolvedVoucherRepositoryImpl implements ResolvedVoucherRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
}
