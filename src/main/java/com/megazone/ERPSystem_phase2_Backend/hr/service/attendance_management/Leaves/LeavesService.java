package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Leaves;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.LeavesCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.LeavesShowDTO;

public interface LeavesService {

    LeavesShowDTO createLeave(LeavesCreateDTO dto);
}
