package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accounting_subject_structure_id")
    private Structure structure; // 계정과목 체계 참조
}