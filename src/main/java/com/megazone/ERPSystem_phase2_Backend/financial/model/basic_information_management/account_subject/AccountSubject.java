package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.EntryType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.IncreaseDecreaseType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 계정과목 테이블
 */
@Entity(name = "account_subject")
@Table(name = "account_subject",
        uniqueConstraints= {
                @UniqueConstraint(
                        columnNames = {
                                "company_id",
                                "code"
                        }
                )
        },
        indexes = {
                @Index(name = "idx_account_subject_code", columnList = "code")
        }
)
@Getter
@Setter
public class AccountSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    private Company company; // 회사 참조

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "structure_code", referencedColumnName = "code")
    private Structure structure; // 계정체계 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code", referencedColumnName = "code")
    private AccountSubject parent;  // 부모 계정과목 참조

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<AccountSubject> child;  // 자식 계정과목 참조

    @OneToMany(mappedBy = "accountSubject", cascade = CascadeType.ALL)
    private List<CashMemo> cashMemo; // 관련 현금적요 목록

    @OneToMany(mappedBy = "accountSubject", cascade = CascadeType.ALL)
    private List<TransferMemo> transferMemo; // 관련 대체적요 목록

    @OneToMany(mappedBy = "accountSubject", cascade = CascadeType.ALL)
    private List<FixedMemo> fixedMemo; // 관련 고정적요 목록

    private String natureCode; // 계정과목 성격 코드

    private String standardFinancialStatementCode; // 표준재무제표 코드


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType entryType;  // 차대구분

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncreaseDecreaseType increaseDecreaseType;  // 증감구분

    @Column(nullable = false)
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

}