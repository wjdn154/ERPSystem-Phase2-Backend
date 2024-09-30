package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;

public interface DailyAndMonthJournalService {
    Object dailyLedgerShow(DailyAndMonthJournalSearchDTO dto);
}
