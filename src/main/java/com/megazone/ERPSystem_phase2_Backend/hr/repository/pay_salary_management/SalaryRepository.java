package com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_salary_management;


import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByEmployeeId(Long employeeId);
//    Optional<Salary> findByEmployeeNumber(String employeeNumber);
}