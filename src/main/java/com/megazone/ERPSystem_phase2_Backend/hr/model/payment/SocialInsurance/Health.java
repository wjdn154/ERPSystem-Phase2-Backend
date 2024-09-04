package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

/*
    건강보험 테이블
*/
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int rate; // 요율 보수월액의 (7.09%, 3.545%, 3.545%)

    @Column
    private BigDecimal LongTermCareInsurance; // 장기요양 보험료 건강보험의(12.95%, 6.475% : 6.475% )

    @Column
    private BigDecimal HiEmployee;

    @Column
    private BigDecimal HiEmployer;
}
