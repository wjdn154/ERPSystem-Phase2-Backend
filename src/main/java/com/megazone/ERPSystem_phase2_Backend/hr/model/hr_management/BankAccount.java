package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;


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

    @OneToOne(mappedBy = "bankAccount",fetch = FetchType.LAZY) // 사원과 1대1 참조
    private Employee employee;

    @Column(nullable = false)
    private String bankName; // 은행 이름

    @Column(nullable = false)
    private String accountNumber; // 계좌번호
}
