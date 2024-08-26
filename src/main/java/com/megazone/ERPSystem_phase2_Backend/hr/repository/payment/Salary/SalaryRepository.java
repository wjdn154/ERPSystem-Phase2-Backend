package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.Salary;


import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByEmployeeId(Long employeeId);
}