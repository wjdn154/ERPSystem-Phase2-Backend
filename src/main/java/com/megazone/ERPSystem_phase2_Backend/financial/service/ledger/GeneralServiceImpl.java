package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.ResolvedVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GeneralServiceImpl implements GeneralService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public GeneralShowAllDTO getGeneralShow(GeneralDTO dto) {
        List<ResolvedVoucher> resolvedVouchers = resolvedVoucherRepository.generalSearch(
                dto.getStartDate(),dto.getEndDate(),
                dto.getStartSubjectCode(),dto.getEndSubjectCode());
        return null;
    }

    public Map<Month, BigDecimal> getMonthlyTotals(List<ResolvedVoucher> resolvedVouchers) {
        // 월별로 그룹화하고, BigDecimal 타입의 amount를 합산
        return resolvedVouchers.stream()
                .collect(Collectors.groupingBy(
                        // 각 ResolvedVoucher의 voucherDate에서 월을 추출하여 그룹화
                        v -> v.getVoucherDate().getMonth(),
                        // 각 그룹에 속한 ResolvedVoucher의 amount(BigDecimal)를 합산
                        Collectors.reducing(
                                BigDecimal.ZERO, // 초기값
                                ResolvedVoucher::getDebitAmount, // 합산할 값 추출
                                BigDecimal::add // 합산 로직
                        )
                ));
        }
}
