package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance.enums.TermCareJoinStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
    건강보험 테이블
*/
@Data
@Entity
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 6 ,scale = 4)
    private int rate; // 요율 보수월액의 (7.09%, 3.545%, 3.545%)

    @Column
    private BigDecimal LongTermCareInsurance; // 장기요양 보험료 건강보험의(12.95%, 6.475% : 6.475% )

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TermCareJoinStatusType joinStatus; // 장기요양 보험 가입여부

    @Column
    private LocalDate ApplyYear; // 요율 적용년도
}
