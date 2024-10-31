package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.TransferType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 발령 기록 저장

@Data
@Entity(name="employee_transfer")
@Table(name="employee_transfer")
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false) // 사원 참조
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_Department_id", nullable = false) // 출발 부서 참조
    private Department fromDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_Department_id", nullable = false) // 도착 부서 참조
    private Department toDepartment;

    @Column(nullable = false)
    private LocalDate transferDate; // 발령 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferType transferType; // 발령 유형 ( promotion :승진, demotion : 강등, lateral : 전보)

    @Column(nullable = false)
    private String reason; // 발령 사유
}
