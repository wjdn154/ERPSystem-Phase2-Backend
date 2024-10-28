package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.EmploymentInsurancePensionShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;

import java.math.BigDecimal;
import java.util.List;

public interface EmploymentInsurancePensionService {
    BigDecimal calculator(InsurancePensionCalculatorDTO dto);

    List<EmploymentInsurancePensionShowDTO> showAll();
}
