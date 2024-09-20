package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsuranceAmount;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "salary")
@Table(name = "salary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // 직원 참조

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "severancepay_id")
//    private SeverancePay severancePay; // 퇴직금 참조

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeductionAmount> deductionAmounts;  // 공제 리스트

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AllowanceAmount> allowanceAmounts;  // 수당 리스트

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "insurance_id")
    private SocialInsuranceAmount socialInsuranceAmount; // 4대 보험 리스트

    // 기본 급여
    @Column
    private BigDecimal baseSalary;

    // 총급여
    @Column
    private BigDecimal grossPay;

    // 보수월액
    @Column
    private BigDecimal NoneTaxSalary;

    // 기준소득월액
    @Column
    private BigDecimal MonthlyIncomeSalary; // 보수월액에서 1000원 미만 제외

    // 실수령액
    @Column
    private BigDecimal netPay;

    // 지급년월
    @Column
    private LocalDate month;

    // 산정기한
    @Column
    private LocalDate calcdate;


    // 보수액 계산
    public void calculateNoneTaxSalary() {
        BigDecimal totalAllowances = allowanceAmounts.stream()
                .filter(allowance -> allowance.getAllowance().isTaxType())
                .map(AllowanceAmount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.NoneTaxSalary = this.baseSalary.add(totalAllowances);
        this.MonthlyIncomeSalary = this.NoneTaxSalary.setScale(-3, RoundingMode.DOWN);
    }
}
