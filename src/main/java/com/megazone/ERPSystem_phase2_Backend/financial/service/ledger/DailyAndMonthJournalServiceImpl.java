package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyAndMonthJournalServiceImpl implements DailyAndMonthJournalService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public Object dailyLedgerShow(DailyAndMonthJournalSearchDTO dto) {
        Object tmp = resolvedVoucherRepository.dailyLedgerList(dto);


        return null;
    }
}
