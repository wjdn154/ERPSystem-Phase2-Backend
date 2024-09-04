package com.megazone.ERPSystem_phase2_Backend.hr.model.payment;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table
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

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Deduction> deductions;  // 공제 리스트
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Allowance> allowances;  // 수당 리스트

    // 기본 급여
    @Column
    private BigDecimal baseSalary;


    // 총급여
    @Column
    private BigDecimal grossPay;

    // 실수령액
    @Column
    private BigDecimal netPay;

    // 지급일
    @Column
    private LocalDate salaryDate;
}
