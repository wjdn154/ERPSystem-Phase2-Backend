package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.Salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_allowance_management.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
    Optional<Allowance> findById(Long id);
}
