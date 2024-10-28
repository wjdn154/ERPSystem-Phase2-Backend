package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.SalaryStep;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.PensionType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.SalaryType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.Salary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryShowDto {
    private Long id;
    private Long SalaryStepId; // 호봉
    private String SalaryStepCode;
    private String SalaryStepName;
    private SalaryType salaryType; // 급여형태
    private boolean incomeTaxType; // 국외소득유무
    private boolean studentLoanRepaymentStatus; // 학자금상환여부
    private BigDecimal studentLoanRepaymentAmount; // 합자금 상환통지액
    private PensionType pensionType; // 연금유형 : 국민연금 or 사학연금
    private BigDecimal nationalPensionAmount; // 국민연금 금액
    private BigDecimal privateSchoolPensionAmount; // 사학연금 금액
    private BigDecimal healthInsurancePensionAmount; // 건강보험 금액
    private String healthInsuranceNumber; // 건강보험 번호
    private String longTermCareInsurancePensionCode; // 장기요양보험 코드
    private BigDecimal employmentInsuranceAmount; // 고용보험 금액
    private boolean unionMembershipStatus; // 노조가입여부

    public static SalaryShowDto create(Salary salary) {
        return new SalaryShowDto(
                salary.getId(),
                salary.getSalaryStep().getId(),
                salary.getSalaryStep().getCode(),
                salary.getSalaryStep().getName(),
                salary.getSalaryType(),
                salary.isIncomeTaxType(),
                salary.isStudentLoanRepaymentStatus(),
                salary.getStudentLoanRepaymentAmount(),
                salary.getPensionType(),
                salary.getNationalPensionAmount(),
                salary.getPrivateSchoolPensionAmount(),
                salary.getHealthInsurancePensionAmount(),
                salary.getHealthInsuranceNumber(),
                salary.getLongTermCareInsurancePensionCode(),
                salary.getEmploymentInsuranceAmount(),
                salary.isUnionMembershipStatus()
        );
    }
}
