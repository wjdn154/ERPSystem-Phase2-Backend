package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance;
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
    private Long id; // 월급 아이디
    private Long employeeId; // 직원 아이디 (필요한가 ?)
    private String employeeNumber; // 직원 코드
    private String firstName; // 직원 이름
    private String lastName; // 직원 이름
    private BigDecimal baseSalary; // 기본 급여
    private List<AllowanceAmount> allowances; // 수당 목록
    private List<DeductionAmount> deductions; // 공제 목록
    private SocialInsurance socialInsurance; // 사회보험 amount
    private BigDecimal netPay; // 실급여
    private LocalDate month; // 급여지급월

    public static SalaryListDTO create(Salary salary) {
        return new SalaryListDTO(
                salary.getId(),
                salary.getEmployee().getId(),
                salary.getEmployee().getEmployeeNumber(),
                salary.getEmployee().getFirstName(),
                salary.getEmployee().getLastName(),
                salary.getBaseSalary(),
                salary.getAllowanceAmounts(),
                salary.getDeductionAmounts(),
                salary.getSocialInsurance(),
                salary.getNetPay(),
                salary.getMonth()
        );

    }

    public void test() {

    }
}
