package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_allowance_management;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


/********* 수당 *********/
@Data
@Entity(name = "allowance")
@Table(name = "allowance")
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "allowance", cascade = CascadeType.ALL)
    private List<AllowanceAmount> allowanceAmounts;

    @Column(nullable = false,unique = true)
    private String code;

    @Column(nullable = false)
    private boolean taxType; // 과세 비과세 구분

    @Column(nullable = false)
    private String name; // 수당 이름
}