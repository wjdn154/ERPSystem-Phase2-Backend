package com.megazone.ERPSystem_phase2_Backend.hr.model.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums.TaxType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/********* 수당 *********/
@Data
@Entity
@Table(name = "allowances")
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxType taxType; // 과세 비과세 구분

    @Column
    private String name; // 수당 이름

    @Column(precision = 9)
    private BigDecimal amount; // 수당 금액

}
