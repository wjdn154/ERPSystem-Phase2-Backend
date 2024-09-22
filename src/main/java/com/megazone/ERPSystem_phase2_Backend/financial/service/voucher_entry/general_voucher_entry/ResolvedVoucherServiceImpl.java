package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class ResolvedVoucherServiceImpl implements ResolvedVoucherService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    /**
     * 미결전표가 승인되었을때 일반전표가 등록되는 메소드
     * @param unresolvedVoucherList 승인된 미결전표 List
     * @return 생성된 일반전표 반환
     */
    @Override
    public void resolvedVoucherEntry(List<UnresolvedVoucher> unresolvedVoucherList) {
        List<ResolvedVoucher> resolvedVoucherList = new ArrayList<>();
        LocalDateTime nowTime = LocalDateTime.now();
        unresolvedVoucherList.stream()
                .forEach(unresolvedVoucher -> {
                    ResolvedVoucher resolvedVoucher = createResolvedVoucher(unresolvedVoucher,nowTime);
                    resolvedVoucherList.add(resolvedVoucherRepository.save(resolvedVoucher));
                });
    }

    /**
     * 일반전표 생성 메소드
     * @param unresolvedVoucher 승인된 미결전표 정보
     * @param approvalTime 미결전표가 승인되어 일반전표가 생성되는 시간
     * @return 생성한 일반전표 객체 반환
     */
    @Override
    public ResolvedVoucher createResolvedVoucher(UnresolvedVoucher unresolvedVoucher, LocalDateTime approvalTime) {
        return ResolvedVoucher.builder()
                .accountSubject(unresolvedVoucher.getAccountSubject())
                .client(unresolvedVoucher.getClient())
                .voucherManager(unresolvedVoucher.getVoucherManager())
                .transactionDescription(unresolvedVoucher.getTransactionDescription())
                .voucherNumber(unresolvedVoucher.getVoucherNumber())
                .voucherType(unresolvedVoucher.getVoucherType())
                .debitAmount(unresolvedVoucher.getDebitAmount())
                .creditAmount(unresolvedVoucher.getCreditAmount())
                .voucherDate(unresolvedVoucher.getVoucherDate())
                .voucherApprovalTime(approvalTime)
                .voucherKind(unresolvedVoucher.getVoucherKind())
                .build();
    }

    @Override
    public List<ResolvedVoucher> resolvedVoucherAllSearch(LocalDate date) {
        List<ResolvedVoucher> resolvedVoucherList = new ArrayList<ResolvedVoucher>();
        try {
            resolvedVoucherList = resolvedVoucherRepository.findByVoucherDateOrderByVoucherNumberAsc(date);
            if(resolvedVoucherList.isEmpty()) {
                throw new NoSuchElementException("해당 날짜에 등록된 전표가 없습니다.");
            }
        }
        catch (NoSuchElementException e) {
            e.getStackTrace();
        }
        return resolvedVoucherList;
    }

    /**
     * 일반전표 전체 출력시 차변, 대변의 합계를 반환하는 메소드
     * @param date 사용자가 조회한 날짜
     * @param amount 차변 또는 대변을 구분하여 실행하기 위한 매개변수
     * @return 차변 또는 대변의 합계를 반환
     */
    @Override
    public BigDecimal calculateTotalAmount(LocalDate date, Function<ResolvedVoucher, BigDecimal> amount) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<ResolvedVoucher> resolvedvoucherList =
                resolvedVoucherRepository.findByVoucherDateOrderByVoucherNumberAsc(date);

        for (ResolvedVoucher voucher : resolvedvoucherList) {
            totalAmount = totalAmount.add(amount.apply(voucher));
        }

        return totalAmount;
    }

    @Override
    public BigDecimal totalDebit(LocalDate date) {
        return calculateTotalAmount(date, ResolvedVoucher::getDebitAmount);
    }

    @Override
    public BigDecimal totalCredit(LocalDate date) {
        return calculateTotalAmount(date, ResolvedVoucher::getCreditAmount);
    }

    @Override
    public List<Long> deleteResolvedVoucher(ResolvedVoucherDeleteDTO dto) {

        // 승인권자면 삭제가능 << 기능구현 필요

        List<Long> deleteVouchers = new ArrayList<>();
        try {
            if(true) { // 승인권자면 삭제가능 << 기능구현 필요
                deleteVouchers.addAll(resolvedVoucherRepository.deleteVoucherByManager(dto));
                if(deleteVouchers.isEmpty()) {
                    throw new NoSuchElementException("검색조건에 해당하는 전표가 없습니다.");
                }
            }
        }
        catch (Exception e) {
            e.getStackTrace();
        }
        return deleteVouchers;
    }
}
