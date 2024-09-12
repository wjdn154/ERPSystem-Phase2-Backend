package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Deduction;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDetailDTO {
    private Long id;
    private Long employeeId;
    private String firstname;
    private String lastname;
    private BigDecimal salary;
    private List<Allowance> allowances;
    private List<Deduction> deductions;
    private SocialInsurance socialInsurance;
    private BigDecimal netPay;

}