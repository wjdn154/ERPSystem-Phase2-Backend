package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
    고용보험 테이블
*/
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 6 ,scale = 4)
    private BigDecimal rate; // 요율 기준소득월액의 (1.8~%, 0.9%, 0.9~%)

    @Column(precision = 6 ,scale = 4)
    private BigDecimal changing_rate; // 사업장 규모에 따른 추가 요율

    @Column(precision = 6 ,scale = 4)
    private BigDecimal upperLimit; // 상한액 617만원

    @Column(precision = 6 ,scale = 4)
    private BigDecimal lowerLimit; // 하한액 39만원

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;



    // 2024 상한액 월 617만원 하한액 39만원 (매년 7월 갱신)
    // 기준소득월액 = 보수월액<비과세 소득을 제외한 급여>에서 천원 미만 금액 절삭한 금액

}
