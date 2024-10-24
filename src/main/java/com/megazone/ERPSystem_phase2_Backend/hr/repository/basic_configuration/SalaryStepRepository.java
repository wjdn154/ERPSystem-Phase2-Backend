package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.SalaryStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryStepRepository extends JpaRepository<SalaryStep, Long> , SalaryStepRepositoryCustom{
    Optional<SalaryStep> findTopByOrderByIdDesc();
}
