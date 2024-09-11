package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QResolvedVoucher;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ResolvedVoucherRepositoryImpl implements ResolvedVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> deleteVoucherByManager(ResolvedVoucherDeleteDTO dto,Long companyId) {
        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;

        List<Long> deletedVoucher = dto.getSearchVoucherNumList().stream()
                .flatMap(voucherNum -> queryFactory.select(qResolvedVoucher.id)
                        .from(qResolvedVoucher)
                        .where(qResolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                .and(qResolvedVoucher.company.id.eq(companyId))
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

    @Override
    public List<GeneralShowDTO> generalSearch(LocalDate startDate, LocalDate endDate, String startAccountCode, String endAccountCode,
                                              Long companyId) {
        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;


        return queryFactory
                .select(
                        qResolvedVoucher.accountSubject.code,
                        qResolvedVoucher.accountSubject.name,
                        qResolvedVoucher.voucherDate.month(),
                        qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class),
                        qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class)
                )
                .from(qResolvedVoucher)
                .where(qResolvedVoucher.voucherDate.between(startDate, endDate)
                        .and(qResolvedVoucher.accountSubject.code.between(startAccountCode, endAccountCode))
                        .and(qResolvedVoucher.company.id.eq(companyId)))
                .groupBy(qResolvedVoucher.accountSubject.code, qResolvedVoucher.voucherDate)
                .orderBy(qResolvedVoucher.voucherDate.asc(), qResolvedVoucher.accountSubject.code.asc())
                .fetch().stream()
                .map(tuple -> new GeneralShowDTO(
                        tuple.get(qResolvedVoucher.accountSubject.code),
                        tuple.get(qResolvedVoucher.accountSubject.name),
                        Month.of(tuple.get(qResolvedVoucher.voucherDate.month())),  // Integer -> Month 변환
                        tuple.get(qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class)),
                        tuple.get(qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class)),
                        BigDecimal.ZERO  // totalCash 값 설정 (필요 시)
                ))
                .collect(Collectors.toList());
    }
}
