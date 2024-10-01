package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.crp;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.CrpDTO;

import java.util.List;

public interface CrpService {
//    void generateCrpForMps(Mps mps);

    CrpDTO createCrp(CrpDTO dto);

    CrpDTO getCrpById(Long id);

    List<CrpDTO> getAllCrps();

    CrpDTO updateCrp(Long id, CrpDTO dto);

    void deleteCrp(Long id);
}
