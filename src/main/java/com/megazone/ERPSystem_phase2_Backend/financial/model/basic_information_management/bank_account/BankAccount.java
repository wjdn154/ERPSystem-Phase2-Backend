package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account;

import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Address;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Bank;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Contact;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 은행 계좌 정보
 */
@Entity(name = "financial_bank_account")
@Table(name = "financial_bank_account")
@Getter
@Setter
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType; // 예금 유형

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank; // 은행 정보

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address; // 주소 정보

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact; // 연락처 정보

    @Column(nullable = false)
    private String code; // 코드

    @Column(nullable = false)
    private String name; // 소유자명

    @Column(nullable = false)
    private String accountNumber; // 계좌번호

    @Column(nullable = false)
    private LocalDate accountOpeningDate; // 계좌 개설일

    private String depositType; // 예금종류

    private LocalDate maturityDate; // 예금 만기일

    private BigDecimal interestRate; // 이자율

    private BigDecimal monthlyPayment; // 매월 납입액

    private BigDecimal overdraftLimit; // 당좌한도액

    private Boolean businessAccount; // 사업용 계좌 여부
}