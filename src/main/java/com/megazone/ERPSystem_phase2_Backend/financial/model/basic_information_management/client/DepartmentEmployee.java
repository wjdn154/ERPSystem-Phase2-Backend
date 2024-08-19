package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 담당 부서 사원 테이블
@Entity(name = "client_department_employee")
@Table(name = "client_department_employee")
@Getter
@Setter
public class DepartmentEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeName; // 사원명

    private String departmentName; // 부서명

    private String employeeCode; // 사원 코드
}
