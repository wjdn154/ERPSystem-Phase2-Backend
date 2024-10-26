package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 부서 정보 저장

@Data
@Entity(name="employee_department")
@Table(name = "employee_department")
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "fromDepartment") // 출발 부서 : Transfer 엔티티에서 출발 부서로 설정된 부서와 연결된 모든 전보 기록을 가지고 있음.
//    private List<Transfer> transferFrom;    // 해당 부서가 직원 이동의 출발 부서였던 모든 Transfer 기록을 가져옴.
//
//    @OneToMany(mappedBy = "toDepartment") // 도착 부서
//    private List<Transfer> transfersTo;

    @Column(name="departmentCode", nullable = false,unique = true)
    private String departmentCode; // 부서번호

    @Column(name="departmentName", nullable = false)
    private String departmentName; // 부서명

    @Column(name="location", nullable = false)
    private String location; // 부서 위치
}