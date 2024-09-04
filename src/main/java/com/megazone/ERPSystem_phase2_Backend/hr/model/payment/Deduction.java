package com.megazone.ERPSystem_phase2_Backend.hr.model.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums.DeductionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/********* 공제 *********/
@Data
@Entity
@Table(name = "deductions")
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(precision = 9)
    private BigDecimal amount;
}
