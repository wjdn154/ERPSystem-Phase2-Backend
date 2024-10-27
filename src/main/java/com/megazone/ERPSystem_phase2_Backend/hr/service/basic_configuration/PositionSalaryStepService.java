package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepShowDTO;

import java.time.YearMonth;
import java.util.List;

public interface PositionSalaryStepService {
    PositionSalaryStepShowAllDTO show(Long positionId);

    PositionSalaryStepShowAllDTO endDateShow(PositionSalaryStepSearchDTO dto);
}
