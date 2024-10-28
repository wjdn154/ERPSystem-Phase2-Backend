package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "salary_ledger")
@Table(name = "salary_ledger")
public class SalaryLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "salary_ledger_date_id")
    private SalaryLedgerDate salaryLedgerDate;

//    // 값 타입 컬렉션으로 수당 관리
//    @ElementCollection
//    @CollectionTable(name = "salary_ledger_allowance", joinColumns = @JoinColumn(name = "salary_ledger_id"))
//    private List<SalaryLedgerAllowance> allowance = new ArrayList<>();

    private boolean finalized = false; // 결산 여부

    @Column(nullable = false)
    private BigDecimal nationalPensionAmount; // 국민연금 금액

    @Column(nullable = false)
    private BigDecimal privateSchoolPensionAmount; // 사학연금 금액

    @Column(nullable = false)
    private BigDecimal healthInsurancePensionAmount; // 건강보험 금액

    @Column(nullable = false)
    private BigDecimal employmentInsuranceAmount; // 고용보험 금액

}

//@Embeddable
//class SalaryLedgerAllowance {
//    private String name; // 수당이름
//    private BigDecimal amount; // 수당금액
//}