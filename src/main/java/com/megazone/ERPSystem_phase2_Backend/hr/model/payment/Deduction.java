package com.megazone.ERPSystem_phase2_Backend.hr.model.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums.DeductionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/********* 공제 *********/
@Data
@Entity
@Table
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name; // 공제 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_id", nullable = false)
    private Salary salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeductionType deductionType; // 공제 유형

//    @Enumerated(EnumType.STRING)
//    @Column
//    private InsuranceType insurance; // 4대보험 유형

    private BigDecimal amount; // 공제 금액
}
