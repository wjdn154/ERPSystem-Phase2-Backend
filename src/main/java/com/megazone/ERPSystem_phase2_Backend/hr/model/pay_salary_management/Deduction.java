package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management;

import jakarta.persistence.*;
import lombok.Data;

/********* 공제 *********/
@Data
@Entity(name = "salary_deduction")
@Table(name = "salary_deduction")
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;
}
