package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 예금 유형
 */
@Entity(name = "bank_account_type")
@Table(name = "bank_account_type")
@Getter
@Setter
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    private String code; // 예금 유형 코드

    @Column(nullable = false, unique = true)
    private String typeName; // 예금 유형명 (보통예금, 당좌예금 등)
}