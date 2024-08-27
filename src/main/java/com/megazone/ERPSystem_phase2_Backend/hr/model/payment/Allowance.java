package com.megazone.ERPSystem_phase2_Backend.hr.model.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums.AllowanceType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/********* 수당 *********/
@Data
@Entity
@Table(name = "allowances")
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name; // 수당 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllowanceType allowanceType; // 수당 유형

    private BigDecimal amount; // 수당 금액



}
