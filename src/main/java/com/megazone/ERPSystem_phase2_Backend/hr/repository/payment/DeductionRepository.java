package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeductionRepository  extends JpaRepository<Deduction, Long> {
    Optional<Deduction> findById(Long id);
}
