package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
    국민연금 테이블
*/
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table
public class National {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 6 ,scale = 4)
    private BigDecimal rate; // 요율 ( 9%, 4.5%, 4.5% )

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createAt; // 생성이력

    @LastModifiedDate
    private LocalDateTime updateAt; // 변경이력
}