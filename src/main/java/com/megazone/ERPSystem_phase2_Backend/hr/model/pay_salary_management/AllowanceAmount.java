package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Entity(name = "salary_allowance_amount")
@Table(name = "salary_allowance_amount")
public class AllowanceAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allowance_id", nullable = false)
    private Allowance allowance;

    @Column(precision = 9)
    private BigDecimal amount; // 수당 금액
}
