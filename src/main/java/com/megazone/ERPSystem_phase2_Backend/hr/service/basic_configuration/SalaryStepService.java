package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryStepEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryStepShowDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SalaryStepService {
    List<SalaryStepShowDTO> show();

    String entry(SalaryStepEntryDTO dto);
}
