package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.IncreaseDecreaseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정과목 표준재무제표 테이블
 */
@Entity(name = "account_subject_standard_financial_statement")
@Table(name = "account_subject_standard_financial_statement")
@Getter
@Setter
public class StandardFinancialStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 표준재무제표 ID

    @Column(nullable = false)
    private String code; // 코드
    @Column(nullable = false)
    private String name; // 한글명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncreaseDecreaseType increaseDecreaseType;  // 증감구분

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private Structure structure; // 계정과목 체계 참조
}