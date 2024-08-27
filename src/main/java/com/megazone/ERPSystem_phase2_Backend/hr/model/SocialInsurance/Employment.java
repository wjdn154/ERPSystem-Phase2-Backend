package com.megazone.ERPSystem_phase2_Backend.hr.model.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


/*
    고용보험 테이블
*/

@Data
@Entity
@Table
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column
    private BigDecimal EmployeeAmount; // 고용자 금액 4.5%

    @Column
    private BigDecimal EmployerAmount; // 고용주 금액 4.5%
}
