package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.CompanyDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.enums.EntityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 회사 기본 정보 테이블
 * 회사 기본 정보 등록시 사용하는 테이블
 */
@Entity(name = "company")
@Table(name = "company")
@NoArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_type_id", nullable = false) // 법인구분
    private CorporateType corporateType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_kinds_id", nullable = false) // 법인종류별 구분
    private CorporateKind corporateKind;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "representative_id", nullable = false) // 대표자 정보
    private Representative representative;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false) // 주소 정보
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false) // 연락처 정보
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_business_code", referencedColumnName = "code", nullable = false) // 주업종 코드
    private MainBusiness mainBusiness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_tax_office_code", referencedColumnName = "code", nullable = false) // 사업장 관할 세무서
    private TaxOffice businessTaxOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarter_tax_office_code", referencedColumnName = "code", nullable = false) // 본점 관할 세무서
    private TaxOffice headquarterTaxOffice;

    @Column(nullable = false) // 지방소득세납세지
    private String localIncomeTaxOffice;

    @Column(nullable = false) // 중소기업여부
    private Boolean isSme;

    @Column(nullable = false, unique = true) // 사업자등록번호
    private String businessRegistrationNumber;

    @Column(nullable = false, unique = true) // 법인등록번호
    private String corporateRegistrationNumber;

    @Column(nullable = false) // 업태
    private String businessType;

    @Column(nullable = false) // 종목
    private String businessItem;

    @Column(nullable = false) // 설립연월일
    private LocalDate establishmentDate;

    @Column(nullable = false) // 개업연월일
    private LocalDate openingDate;

    @Column(nullable = false) // 폐업연월일
    private LocalDate closingDate;

    @Column(nullable = false) // 회사명
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // 구분 (법인, 개인)
    private EntityType entityType;

    @Column(nullable = false) // 회계연도 시작일
    private LocalDate fiscalYearStart;

    @Column(nullable = false) // 회계연도 마지막일
    private LocalDate fiscalYearEnd;

    @Column(nullable = false) // 회계연도 기수
    private Long fiscalCardinalNumber;

    public Company(CorporateType corporateType, CorporateKind corporateKind, Representative representative, Address address, Contact contact, MainBusiness mainBusiness, TaxOffice businessTaxOffice, TaxOffice headquarterTaxOffice, String localIncomeTaxOffice, Boolean isSme, String businessRegistrationNumber, String corporateRegistrationNumber, String businessType, String businessItem, LocalDate establishmentDate, LocalDate openingDate, LocalDate closingDate, String name, EntityType entityType, LocalDate fiscalYearStart, LocalDate fiscalYearEnd, Long fiscalCardinalNumber) {
        this.corporateType = corporateType;
        this.corporateKind = corporateKind;
        this.representative = representative;
        this.address = address;
        this.contact = contact;
        this.mainBusiness = mainBusiness;
        this.businessTaxOffice = businessTaxOffice;
        this.headquarterTaxOffice = headquarterTaxOffice;
        this.localIncomeTaxOffice = localIncomeTaxOffice;
        this.isSme = isSme;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.corporateRegistrationNumber = corporateRegistrationNumber;
        this.businessType = businessType;
        this.businessItem = businessItem;
        this.establishmentDate = establishmentDate;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.name = name;
        this.entityType = entityType;
        this.fiscalYearStart = fiscalYearStart;
        this.fiscalYearEnd = fiscalYearEnd;
        this.fiscalCardinalNumber = fiscalCardinalNumber;
    }
}