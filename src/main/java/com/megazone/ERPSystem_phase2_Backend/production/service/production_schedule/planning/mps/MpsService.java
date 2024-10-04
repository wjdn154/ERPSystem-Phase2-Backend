package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mps;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MpsDTO;

import java.util.List;

public interface MpsService {
    MpsDTO createMps(MpsDTO dto);

    List<MpsDTO> getAllMps();

    MpsDTO getMpsById(Long id);

    MpsDTO updateMps(Long id, MpsDTO dto);

    String deleteMps(Long id);

}
