package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity(name = "employee_transfer_type")
@Table(name = "employee_transfer_type")
@NoArgsConstructor
@AllArgsConstructor
public class TransferType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // 발령 유형 코드 (ex: PROMOTION, DEMOTION, LATERAL)

    @Column
    private String description; // 발령 유형 설명 (ex: 승진, 강등, 전보)

}
