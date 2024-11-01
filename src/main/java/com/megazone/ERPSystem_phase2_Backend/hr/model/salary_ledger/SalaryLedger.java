package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 급여계산 및 입력 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "salary_ledger")
@Table(name = "salary_ledger")
public class SalaryLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "salary_ledger_date_id")
    private SalaryLedgerDate salaryLedgerDate;

    // 값 타입 컬렉션으로 수당 관리
    @ElementCollection
    @CollectionTable(name = "salary_ledger_allowance", joinColumns = @JoinColumn(name = "salary_ledger_id"))
    private List<SalaryLedgerAllowance> allowance = new ArrayList<>();

    private boolean finalized; // 결산 여부

    @Column(nullable = false)
    private BigDecimal nationalPensionAmount; // 국민연금 금액

    @Column(nullable = false)
    private BigDecimal privateSchoolPensionAmount; // 사학연금 금액

    @Column(nullable = false)
    private BigDecimal healthInsurancePensionAmount; // 건강보험 금액

    @Column(nullable = false)
    private BigDecimal employmentInsuranceAmount; // 고용보험 금액
    
    @Column(nullable = false)
    private BigDecimal longTermCareInsurancePensionAmount; // 장기요양보험 금액

    @Column(nullable = false)
    private BigDecimal incomeTaxAmount; // 소득세 금액

    @Column(nullable = false)
    private BigDecimal localIncomeTaxAmount; // 지방소득세 금액


    @Column(nullable = false)
    private BigDecimal totalSalaryAmount; // 지급총액
    @Column(nullable = false)
    private BigDecimal nonTaxableAmount; // 비과세 금액
    @Column(nullable = false)
    private BigDecimal taxableAmount; // 과세 금액
    @Column(nullable = false)
    private BigDecimal taxableIncome;    // 과세소득
    @Column(nullable = false)
    private BigDecimal totalDeductionAmount; // 공제총액
    @Column(nullable = false)
    private BigDecimal netPayment; // 차인지급액

}

