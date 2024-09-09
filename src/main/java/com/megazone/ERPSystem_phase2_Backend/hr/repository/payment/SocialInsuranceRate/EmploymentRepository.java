package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.SocialInsuranceRate;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface EmploymentRepository extends JpaRepository<Employment, Long> {
}
