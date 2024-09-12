package com.megazone.ERPSystem_phase2_Backend.hr.service.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryListDTO;

import java.util.List;

public interface SalaryService {
    List<SalaryListDTO> findAllEmployeesAndSalary();
}