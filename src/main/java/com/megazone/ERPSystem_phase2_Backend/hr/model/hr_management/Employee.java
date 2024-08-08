package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 사원 기본 정보 저장

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_Id", nullable = false) // 부서 참조
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_Id", nullable = false) // 직위 참조
    private Position position;

    @OneToMany(mappedBy = "performance_Id", fetch = FetchType.LAZY) // 성과 평가 참조
    private List<Performance> performance;

    @OneToMany(mappedBy = "transfer_Id", fetch = FetchType.LAZY) // 발령 참조
    private List<Transfer> transfer;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "salary_Id", nullable = false)
//    private Salary salary;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "allowance_Id", nullable = false)
//    private Allowance allowance;

    @Column(nullable = false)
    private String firstName; // 성

    @Column(nullable = false)
    private String lastName; // 이름

    @Column(nullable = false)
    private Date dateOfBirth; // 생년월일

    @Column(nullable = false)
    private String phoneNumber; // 휴대폰 번호

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private LocalDate hireDate; // 고용일
}
