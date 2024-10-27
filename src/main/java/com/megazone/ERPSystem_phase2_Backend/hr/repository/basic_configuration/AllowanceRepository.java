package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllowanceRepository extends JpaRepository<Allowance, Integer>, AllowanceRepositoryCustom {
    Optional<Allowance> findTopByOrderByIdDesc();
}
