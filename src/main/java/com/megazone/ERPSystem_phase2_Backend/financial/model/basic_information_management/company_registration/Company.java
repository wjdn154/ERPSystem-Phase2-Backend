package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.enums.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 회사 기본 정보 테이블
 * 회사 기본 정보 등록시 사용하는 테이블
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false) // 고유식별자
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_type_id", nullable = false) // 법인구분
    private CorporateType corporateType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_kinds_id", nullable = false) // 법인종류
    private CorporateKind corporateKinds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "representative_id", nullable = false) // 대표자 정보
    private Representative representative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false) // 주소 정보
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false) // 연락처 정보
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_tax_office_id", nullable = false) // 사업장 관할 세무서
    private TaxOffice businessTaxOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarters_tax_office_id", nullable = false) // 본점 관할 세무서
    private TaxOffice headquartersTaxOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_type_id", nullable = false) // 업종 형태
    private BusinessType businessType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_item_id", nullable = false) // 업체 종목
    private BusinessItem businessItem;

    @Column(nullable = false) // 지방소득세납세지
    private String localIncomeTaxOffice;

    @Column(nullable = false) // 중소기업여부
    private Boolean isSme;

    @Column(nullable = false) // 사업자등록번호
    private String businessRegistrationNumber;

    @Column(nullable = false) // 법인등록번호
    private String corporateRegistrationNumber;

    @Column(nullable = false) // 설립연월일
    private LocalDate establishmentDate;

    @Column(nullable = false) // 회사명
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // 구분 (법인, 개인)
    private EntityType entityType;

    @Column(nullable = false) // 사용여부(사용, 미사용)
    private Boolean active;

    @Column(nullable = false) // 회계연도 시작일
    private LocalDate fiscalYearStart;

    @Column(nullable = false) // 회계연도 마지막일
    private LocalDate fiscalYearEnd;

    @Column(nullable = false) // 회계연도 기수
    private Integer fiscalCardinalNumber;
}