package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepShowDTO;

import java.time.YearMonth;
import java.util.List;

public interface PositionSalaryStepService {
    List<PositionSalaryStepShowDTO> show(Long positionId);

    List<PositionSalaryStepShowDTO> endDateShow(PositionSalaryStepSearchDTO dto);
}
