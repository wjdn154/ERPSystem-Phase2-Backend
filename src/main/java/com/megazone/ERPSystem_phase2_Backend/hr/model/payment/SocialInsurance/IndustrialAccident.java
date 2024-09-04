package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
    산재보험 테이블
*/
@Entity
@Data
public class IndustrialAccident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 6 ,scale = 4)
    private int rate; // 요율

    @Column
    private LocalDate ApplyYear; // 요율 적용년도
}
