package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.PensionType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.SalaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee_salary")
@Table(name = "employee_salary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long SalaryStepId; // 호봉

    @Column(nullable = false)
    private SalaryType salaryType; // 급여형태

    @Column(nullable = false)
    private boolean incomeTaxType; // 국외소득유형

    @Column(nullable = false)
    private boolean studentLoanRepaymentStatus; // 학자금상환여부
    private BigDecimal studentLoanRepaymentAmount; // 합자금 상환통지액

    @Column(nullable = false)
    private PensionType pensionType; // 연금유형 : 국민연금 or 사학연금
    private BigDecimal nationalPensionAmount; // 국민연금 금액

    private BigDecimal privateSchoolPensionAmount; // 사학연금 금액

    private BigDecimal healthInsurancePensionAmount; // 건강보험 금액
    private String healthInsuranceNumber; // 건강보험 번호

    private BigDecimal employmentInsuranceAmount; // 고용보험 금액

    private boolean unionMembershipStatus; // 노조가입여부

    private boolean duruNuriSocialInsuranceStatus; // 두루누리사회보험 여부
    private BigDecimal duruNuriSocialInsuranceRate; // 두루누리적용률 30 40(구) 60(구) 80 90

}
