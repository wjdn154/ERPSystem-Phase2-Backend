package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowDTO;

import java.util.List;

public interface GeneralService {
    List<GeneralShowDTO> getGeneralShow(GeneralDTO dto);
}
