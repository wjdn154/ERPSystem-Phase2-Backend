package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee_income_tax")
@Table(name = "employee_income_tax")
public class IncomeTax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unique
    private Long id;

    private String code; // 과세표준 코드

    private BigDecimal lowAmount; // 과세표준 하한

    private BigDecimal highAmount; // 과세표준 상한

    private BigDecimal rate; // 세율
    
    private BigDecimal taxCredit; // 누진공제액
}
