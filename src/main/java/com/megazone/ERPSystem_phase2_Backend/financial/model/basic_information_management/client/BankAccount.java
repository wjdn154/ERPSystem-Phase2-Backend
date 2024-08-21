package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Bank;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 은행 계좌 정보 테이블
@Entity(name = "client_bank_account")
@Table(name = "client_bank_account")
@Getter
@Setter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank; // 은행 코드 참조

    @Column(nullable = false)
    private String accountNumber; // 계좌번호

    @Column(nullable = false)
    private String accountHolder; // 예금주
}
