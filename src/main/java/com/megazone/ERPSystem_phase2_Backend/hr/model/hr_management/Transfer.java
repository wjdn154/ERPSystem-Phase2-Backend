package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 발령 기록 저장

@Data
@Entity
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
    private Date transferDate; // 발령 날짜

    @Column(nullable = false)
    private String transferType; // 발령 유형 ( promotion :승진, demotion : 강등, lateral : 전보)

    @Column(nullable = false)
    private String reason; // 발령 사유
}
