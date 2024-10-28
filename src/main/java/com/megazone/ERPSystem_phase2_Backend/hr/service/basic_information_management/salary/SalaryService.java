package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryShowDto;

public interface SalaryService {
    void saveSalary(SalaryEntryDTO dto);

    SalaryShowDto show(Long employeeId);
}
