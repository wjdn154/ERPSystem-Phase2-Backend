package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Performance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    void deleteByEvaluatorId(long evaluatorId);
}
