package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryDTO {
    private Long employeeId;
    private Long salaryId;
    private BigDecimal baseSalary;
    private BigDecimal grossPay;
    private BigDecimal allowances;
    private BigDecimal deductions;
    private BigDecimal employmentAmount;
    private BigDecimal healthAmount;
    private BigDecimal insuranceAmount;
    private BigDecimal nationalAmount;
    private BigDecimal netPay;
    private LocalDate salaryDate;
}
