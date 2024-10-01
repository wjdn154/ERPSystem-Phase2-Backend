package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkPerformanceRepository extends JpaRepository<WorkPerformance, Long> , WorkPerformanceRepositoryCustom {
}
