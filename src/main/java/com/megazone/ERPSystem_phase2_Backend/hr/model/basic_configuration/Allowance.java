package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 수당
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee_allowance")
@Table
public class Allowance {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false,unique = true)
    private String code; // 수당코드

    @Column(nullable = false)
    private BigDecimal amount; // 수당금액

    private String description; // 비고
}