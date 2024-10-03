package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalShowDTO;

import java.util.List;

public interface DailyAndMonthJournalService {
    List<DailyAndMonthJournalShowDTO> dailyLedgerShow(DailyAndMonthJournalSearchDTO dto);
}
