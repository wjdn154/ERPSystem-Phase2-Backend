package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Address;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

// 거래처 테이블
@Entity(name = "client")
@Table(name = "client")
@Getter
@Setter
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // 거래처 코드

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;  // 주소 정보

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_info_id")
    private BusinessInfo businessInfo; // 업태 및 종목

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo; // 연락처 정보

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "financial_info_id")
    private FinancialInfo financialInfo; // 재무 정보

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_info_id")
    private ManagerInfo managerInfo; // 업체 담당자 정보

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // 담당자

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // 거래처 분류 코드

    @ManyToOne
    @JoinColumn(name = "liquor_id")
    private Liquor liquor; // 주류 코드 참조

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount; // 은행 계좌 정보 참조

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 거래 유형 (매출, 매입, 동시)

    private String businessRegistrationNumber; // 사업자등록번호

    private String idNumber; // 주민등록번호

    private String representativeName; // 대표자명

    private String printClientName; // 거래처명

    private LocalDate transactionStartDate; // 거래 시작일

    private LocalDate transactionEndDate; // 거래 종료일

    private String remarks; // 비고

    private Boolean isActive; // 사용 여부
}