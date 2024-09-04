package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
    국민연금 테이블
*/
@Entity
@Data
public class National {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 6 ,scale = 4)
    private int rate; // 요율 ( 9%, 4.5%, 4.5% )

    @Column
    private LocalDate ApplyYear; // 요율 적용년도
}