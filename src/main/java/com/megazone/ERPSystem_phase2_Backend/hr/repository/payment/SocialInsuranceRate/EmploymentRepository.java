package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.SocialInsuranceRate;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentRepository extends JpaRepository<Employment, Long> {
}
