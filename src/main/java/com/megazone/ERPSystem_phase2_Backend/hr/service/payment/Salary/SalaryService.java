package com.megazone.ERPSystem_phase2_Backend.hr.service.payment.Salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto.SalaryDTO;

import java.util.Optional;

public interface SalaryService {
//    Optional<SalaryDTO> findSalaryById(Long id);

//    Optional<SalaryDTO> findSalaryByEmployeeId(Long id);

    Optional<Salary> getSalaryByEmployeeId(Long employeeId);

    Salary saveOrUpdateSalary(Salary salary);

    void deleteSalary(Long salaryid);
}