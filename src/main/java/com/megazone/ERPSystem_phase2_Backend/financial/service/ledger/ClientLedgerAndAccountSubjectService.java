package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;

import java.util.List;

public interface ClientLedgerAndAccountSubjectService {
    List<ClientListDTO> show(ClientAndAccountSubjectLedgerSearchDTO dto);

    ClientAndAccountSubjectLedgerShowDetailAllDTO showDetail(ClientAndAccountSubjectLedgerShowDetailConditionDTO dto);
}
