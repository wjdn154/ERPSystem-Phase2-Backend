package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/*
    고용보험 테이블
*/
@Entity
@Data
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    private Employee employee // 사업장 규모 파악을 위한 사원수 정보

    @Column
    private int rate; // 요율 보수월액의 (1.8~%, 0.9%, 0.9~%)

    @Column
    private int changing_rate; // 사업장 규모에 따른 추가 요율

    @Column
    private BigDecimal EiEmployee; // 근로자

    @Column
    private BigDecimal EiEmployer; // 고용주


}
