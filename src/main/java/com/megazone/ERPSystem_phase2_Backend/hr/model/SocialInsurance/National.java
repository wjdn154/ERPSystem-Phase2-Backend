package com.megazone.ERPSystem_phase2_Backend.hr.model.SocialInsurance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.SocialInsurance.enums.NationalType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/*
    국민연금 테이블
*/
@Data
@Entity
@Table
public class National {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NationalType nationalType;

    private BigDecimal amount;
}
