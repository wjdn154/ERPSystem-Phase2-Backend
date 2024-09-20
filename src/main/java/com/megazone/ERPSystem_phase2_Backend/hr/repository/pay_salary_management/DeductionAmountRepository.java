package com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_salary_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.DeductionAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeductionAmountRepository extends JpaRepository<DeductionAmount, Long> {
}
