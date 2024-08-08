package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 부서 정보 저장

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;

    @OneToMany(mappedBy = "fromDepartment") // 출발 부서
    private List<Transfer> transferFrom;

    @OneToMany(mappedBy = "toDepartment") // 도착 부서
    private List<Transfer> transfersTo;

    @Column(nullable = false)
    private String departmentName; // 부서명

    @Column(nullable = false)
    private String Location; // 부서 위치

    @Column(nullable = false)
    private String managerId;
}
