package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.socialInsurance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance.SocialInsurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialInsuranceRepository extends JpaRepository <SocialInsurance, Long> {
}
