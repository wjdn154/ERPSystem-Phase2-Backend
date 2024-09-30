package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Performance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByEmployeeId(Long employeeId); // 특정 사원의 성과 평가 조회
    List<Performance> findByEvaluatorId(Long evaluatorId); // 특정 평가자가 한 평가 조회

    void deleteByEvaluatorId(Long id);
}
