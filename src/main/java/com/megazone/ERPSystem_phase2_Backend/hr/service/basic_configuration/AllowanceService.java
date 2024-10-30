package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceShowDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AllowanceService {
    List<AllowanceShowDTO> show();

    String entry(AllowanceEntryDTO dto);

    BigDecimal taxableCalculator(BigDecimal amount, Long allowanceId);
}
