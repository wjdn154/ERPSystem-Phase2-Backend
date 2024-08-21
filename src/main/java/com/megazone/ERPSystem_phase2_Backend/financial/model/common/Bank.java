package com.megazone.ERPSystem_phase2_Backend.financial.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "financial_bank")
@Table(name = "financial_bank")
@Getter
@Setter
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // 은행 코드

    @Column(nullable = false)
    private String name; // 은행명

    private String businessNumber; // 사업자등록번호

}