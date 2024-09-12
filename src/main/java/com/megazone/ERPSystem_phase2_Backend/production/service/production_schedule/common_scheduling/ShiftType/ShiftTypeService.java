package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ShiftType;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ShiftTypeDTO;

import java.util.List;

public interface ShiftTypeService {
    List<ShiftTypeDTO> getAllShiftTypes(Long companyId);
    ShiftTypeDTO getShiftTypeById(Long id, Long companyId);
    ShiftTypeDTO createShiftType(ShiftTypeDTO shiftTypeDTO, Long companyId);
    ShiftTypeDTO updateShiftType(ShiftTypeDTO shiftTypeDTO, Long companyId);
    void deleteShiftType(Long id, Long companyId);
}
