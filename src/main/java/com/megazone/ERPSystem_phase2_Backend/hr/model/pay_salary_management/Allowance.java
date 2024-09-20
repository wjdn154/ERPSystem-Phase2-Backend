package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


/********* 수당 *********/
@Data
@Entity(name = "salary_allowance")
@Table(name = "salary_allowance")
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "allowance", cascade = CascadeType.ALL)
//    private List<AllowanceAmount> allowanceAmounts;

    @Column(nullable = false,unique = true)
    private String code;

    @Column(nullable = false)
    private boolean taxType; // 과세 비과세 구분 (true: 과세, false: 비과세)

    @Column(nullable = false)
    private String name; // 수당 이름
}