package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowAllListDTO;

public interface CashJournalService {
    CashJournalShowAllListDTO showAll(CashJournalSearchDTO dto);
}
