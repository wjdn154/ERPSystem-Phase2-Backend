package com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.EnvironmentalCertificationAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.Optional;

public interface EnvironmentalCertificationAssessmentRepository extends JpaRepository<EnvironmentalCertificationAssessment, Long> {
    Optional<EnvironmentalCertificationAssessment> findByMonth(YearMonth month);
}