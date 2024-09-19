package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;

import java.util.List;

public interface GeneralService {
    List<GeneralAccountListDTO> getGeneralShow(GeneralDTO dto);

    GeneralShowAllDTO getGeneralSelectShow(GeneralSelectDTO dto);
}
