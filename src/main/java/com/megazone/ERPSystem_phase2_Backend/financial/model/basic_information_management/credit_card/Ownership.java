package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

// 신용카드 소유 및 담당자 정보
@Entity(name = "credit_card_ownership")
@Table(name = "credit_card_ownership")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ownership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id", nullable = false)
    private CreditCard creditCard; // 신용카드 참조

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_employee_id", nullable = false)
    private Employee owner; // 카드 소유 담당 사원

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager; // 담당자
}