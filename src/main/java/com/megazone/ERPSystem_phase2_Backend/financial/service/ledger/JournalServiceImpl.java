package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JournalServiceImpl implements JournalService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;
    private final UnresolvedVoucherRepository unresolvedVoucherRepository;


    @Override
    public List<UnresolvedVoucher> journalSearch(LocalDate StartDate, LocalDate EndDate, Long companyId) {
        return unresolvedVoucherRepository.journalSearch(StartDate, EndDate,companyId);
    }

    @Override
    public List<BigDecimal> journalTotalAmount(LocalDate StartDate, LocalDate EndDate, Long companyId) {
        List<BigDecimal> amounts = new ArrayList<>();
        amounts.add(unresolvedVoucherRepository.testTotalDebit(StartDate, EndDate,companyId));
        amounts.add(unresolvedVoucherRepository.testTotalCredit(StartDate, EndDate,companyId));

        return amounts;
    }

    @Override
    public BigDecimal journalTotalCount(LocalDate StartDate, LocalDate EndDate, Long companyId) {
        return unresolvedVoucherRepository.journalTotalCount(StartDate, EndDate,companyId);
    }
}
