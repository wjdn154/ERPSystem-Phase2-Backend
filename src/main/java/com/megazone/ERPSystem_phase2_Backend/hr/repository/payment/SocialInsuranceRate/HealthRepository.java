package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.SocialInsuranceRate;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Long> {
}
