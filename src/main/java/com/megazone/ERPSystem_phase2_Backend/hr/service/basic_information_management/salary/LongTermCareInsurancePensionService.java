package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceShowDTO;

import java.math.BigDecimal;
import java.util.List;

public interface LongTermCareInsurancePensionService {
    List<LongTermCareInsuranceShowDTO> showAll();

    BigDecimal calculator(LongTermCareInsuranceCalculatorDTO dto);
}
