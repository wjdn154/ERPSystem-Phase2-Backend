package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.EntryType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.IncreaseDecreaseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 계정과목 테이블
 */
@Entity(name = "account_subject")
@Table(name = "account_subject")
@Getter
@Setter
public class AccountSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 계정과목 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accounting_subject_structure_id")
    private Structure structure; // 계정체계 참조

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accounting_subject_standard_financial_statement_id")
    private StandardFinancialStatement standardFinancialStatement; // 표준 재무제표 참조

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accounting_subject_nature_id", nullable = false)
    private Nature nature; // 계정과목 성격 참조

    @OneToMany(mappedBy = "accountSubject", fetch = FetchType.LAZY)
    private List<CashMemo> cashMemo; // 관련 현금적요 목록

    @OneToMany(mappedBy = "accountSubject", fetch = FetchType.LAZY)
    private List<TransferMemo> transferMemo; // 관련 대체적요 목록

    @OneToMany(mappedBy = "accountSubject", fetch = FetchType.LAZY)
    private List<FixedMemo> fixedMemo; // 관련 고정적요 목록

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType entryType;  // 차대구분

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncreaseDecreaseType increaseDecreaseType;  // 증감구분

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
    private String relationshipCode;  // 관계 계정과목 코드

}