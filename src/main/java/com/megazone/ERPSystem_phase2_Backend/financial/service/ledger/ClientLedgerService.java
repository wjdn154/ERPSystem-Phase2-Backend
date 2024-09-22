package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.ClientLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.ClientLedgerShowAllDTO;

public interface ClientLedgerService {
    ClientLedgerShowAllDTO show(ClientLedgerSearchDTO dto);
}
