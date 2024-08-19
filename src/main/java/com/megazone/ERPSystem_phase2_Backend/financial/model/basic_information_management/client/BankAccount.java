package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
