package com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.Salary;


import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}