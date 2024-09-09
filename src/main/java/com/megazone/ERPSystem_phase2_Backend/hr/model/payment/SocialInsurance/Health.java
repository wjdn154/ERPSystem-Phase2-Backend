package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance.enums.TermCareJoinStatusType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/*
    건강보험 테이블
*/
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 6 ,scale = 4)
    private BigDecimal rate; // 요율 보수월액의 (7.09%, 3.545%, 3.545%)

    @Column(precision = 6 ,scale = 4)
    private BigDecimal LongTermCareInsurance; // 장기요양 보험료 건강보험의(12.95%, 6.475% : 6.475% )

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TermCareJoinStatusType joinStatus; // 장기요양 보험 가입여부

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}
