package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_allowance_management.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_deduction_management.Deduction;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance.SocialInsurance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// 사원 급여 모두 보이는 리스트

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryListDTO {
    private Long id;
    private Long employeeId;
    private String employeeNumber;
    private BigDecimal baseSalary;
    private List<Allowance> allowances;
    private List<Deduction> deductions;
    private SocialInsurance socialInsurance;
    private BigDecimal netPay;
    private LocalDate month;
}
