package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
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


    @Override
    public List<ResolvedVoucher> journalSearch(LocalDate StartDate, LocalDate EndDate) {
        return resolvedVoucherRepository.journalSearch(StartDate, EndDate);
    }

    @Override
    public List<BigDecimal> journalTotalAmount(LocalDate StartDate, LocalDate EndDate) {
        List<BigDecimal> amounts = new ArrayList<>();
        amounts.add(resolvedVoucherRepository.testTotalDebit(StartDate, EndDate));
        amounts.add(resolvedVoucherRepository.testTotalCredit(StartDate, EndDate));

        return amounts;
    }

    @Override
    public BigDecimal journalTotalCount(LocalDate StartDate, LocalDate EndDate) {
        return resolvedVoucherRepository.testJournalTotalCount(StartDate, EndDate);
    }
}
