package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 계정과목 테이블
 */
@Entity
@Table(name = "account_subject")
@Getter
@Setter
public class AccountSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 계정과목 ID

    @Column(unique = true, nullable = false)
    private String code; // 계정과목 코드
    @Column(nullable = false)
    private String name; // 계정과목명
    private String englishName; // 영문명
    @Column(nullable = false)
    private Boolean isActive; // 계정 사용 여부
    @Column(nullable = false)
    private Boolean modificationType; // 계정과목 수정가능 여부
    @Column(nullable = false)
    private Boolean isForeignCurrency; // 외화 사용 여부
    @Column(nullable = false)
    private Boolean isBusinessCar; // 업무용 차량 여부

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accounting_structure_id")
    private AccountSubjectStructure accountSubjectStructure; // 계정체계 참조

    @OneToMany(mappedBy = "accountSubject", fetch = FetchType.LAZY)
    private List<AccountSubjectCashMemo> accountSubjectCashMemo; // 관련 현금적요 목록

    @OneToMany(mappedBy = "accountSubject", fetch = FetchType.LAZY)
    private List<AccountSubjectTransferMemo> accountSubjectTransferMemo; // 관련 대체적요 목록

    @OneToMany(mappedBy = "accountSubject", fetch = FetchType.LAZY)
    private List<AccountSubjectFixedMemo> accountSubjectFixedMemos; // 관련 고정적요 목록

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "standard_financial_statement_id")
    private AccountSubjectStandardFinancialStatement accountSubjectStandardFinancialStatement; // 표준 재무제표 참조
}