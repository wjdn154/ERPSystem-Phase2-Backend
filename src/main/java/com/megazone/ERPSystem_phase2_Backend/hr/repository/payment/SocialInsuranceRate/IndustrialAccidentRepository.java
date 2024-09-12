package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.SocialInsuranceRate;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance.IndustrialAccident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustrialAccidentRepository extends JpaRepository<IndustrialAccident, Long> {
}
