package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Performance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Performance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceOneDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceShowDTO;

import java.util.List;

public interface PerformanceService {

    PerformanceDTO addPerformance(PerformanceCreateDTO performanceDTO);

    PerformanceShowDTO updatePerformance(Long performanceId, PerformanceOneDTO performanceOneDTO);

    String deletePerformance(Long performanceId);


    List<PerformanceShowDTO> getPerformanceByEmployee(Long employeeId);

    List<PerformanceShowDTO> getAllPerformances();
}
