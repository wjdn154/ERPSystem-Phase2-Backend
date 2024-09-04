package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

/*
    국민연금 테이블
*/
public class National {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int rate; // 요율 ( 9%, 4.5%, 4.5% )

    @Column
    private BigDecimal NiEmployee;

    @Column BigDecimal NiEmployer;
}