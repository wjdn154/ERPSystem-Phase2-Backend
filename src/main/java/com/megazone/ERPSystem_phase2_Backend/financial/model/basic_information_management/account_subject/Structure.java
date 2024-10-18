package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.FinancialStatementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정과목 체계 테이블
 */
@Entity(name = "account_subject_structure")
@Table(name = "account_subject_structure")
@Getter
@Setter
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 계정과목 체계 ID

    @Column(unique = true, nullable = false)
    private String code; // 계정과목 체계 코드
    @Column(nullable = false)
    private String largeCategory; // 대분류 (예: 자산, 부채, 자본)
    @Column(nullable = false)
    private String mediumCategory; // 중분류 (예: 유동자산, 비유동자산)
    @Column(nullable = false)
    private String smallCategory; // 소분류 (예: 당좌자산, 재고자산)
    @Column(nullable = false)
    private Integer min; // 최소값
    @Column(nullable = false)
    private Integer max; // 최대값

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinancialStatementType financialStatementType; // 재무재표 반영 유형
}