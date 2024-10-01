package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mrp;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MaterialInputStatusDto;

import java.util.List;

public interface MaterialInputStatusService {
    MaterialInputStatusDto createMaterialInputStatus(MaterialInputStatusDto dto);
    MaterialInputStatusDto getMaterialInputStatusById(Long id);
    List<MaterialInputStatusDto> getAllMaterialInputStatuses();
    MaterialInputStatusDto updateMaterialInputStatus(Long id, MaterialInputStatusDto dto);
    void deleteMaterialInputStatus(Long id);
}
