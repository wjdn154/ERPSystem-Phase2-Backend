package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.PositionSalaryStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PositionSalaryStepRepository extends JpaRepository<PositionSalaryStep, Long> , JpaSpecificationExecutor<PositionSalaryStep> {

}
