package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceListDTO;

import java.util.List;
import java.util.Optional;

public interface WorkPerformanceService {
    List<WorkPerformanceListDTO> findAllWorkPerformance();

    Optional<WorkPerformanceDetailDTO> findWorkPerformanceById(Long id);

    Optional<WorkPerformanceDetailDTO> createWorkPerformance(WorkPerformanceDetailDTO dto);

    Optional<WorkPerformanceDetailDTO> updateWorkPerformance(Long id, WorkPerformanceDetailDTO dto);

}
