package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "deduction_amount")
@Table(name = "deduction_amount")
public class DeductionAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deduction_id", nullable = false)
    private Deduction deduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_id", nullable = false)
    private Salary salary;

    private BigDecimal amount;
}

