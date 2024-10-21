package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;


import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Bank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 계좌번호 테이블

@Data
@Table(name="employee_bank_account")
@Entity(name="employee_bank_account")
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(nullable = false)
    private String accountNumber; // 계좌번호
}
