package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.AllowanceAmount;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.DeductionAmount;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsuranceAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryUpdateDTO {
    private Long id; // 월급 아이디
    private Long employeeId; // 직원 아이디 (필요한가 ?)
    private String employeeNumber; // 직원 코드
    private String firstName; // 직원 이름
    private String lastName; // 직원 이름
    private BigDecimal baseSalary; // 기본 급여
    private List<AllowanceAmount> allowances; // 수당 목록
    private List<DeductionAmount> deductions; // 공제 목록
    private SocialInsuranceAmount socialInsuranceAmount; // 사회보험 amount
    private BigDecimal grossPay; // 총급여
    private BigDecimal netPay; // 실급여
    private LocalDate month; // 급여지급월
}
