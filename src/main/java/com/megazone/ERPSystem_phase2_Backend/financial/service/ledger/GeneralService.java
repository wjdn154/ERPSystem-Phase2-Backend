package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowAllDTO;

public interface GeneralService {
    GeneralShowAllDTO getGeneralShow(GeneralDTO dto);
}
