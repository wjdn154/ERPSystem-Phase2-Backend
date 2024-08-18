package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.enums.EmploymentType;
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
    @JoinColumn(name = "department_id", nullable = false) // 부서 참조
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false) // 직위 참조
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobTitle_id", nullable = false) // 직책 참조
    private JobTitle jobTitle;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY) // 성과 평가 참조
    private List<Performance> performance;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY) // 발령 참조
    private List<Transfer> transfer;

    @OneToOne(mappedBy = "employee",fetch = FetchType.LAZY) // Users 랑 1대1 참조
    private Users users;

    @OneToOne(cascade = CascadeType.ALL) // 계좌번호랑 1대 1참조
    @JoinColumn(name = "bankaccount_id")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY) // 휴가 참조
    private List<Leaves> leaves;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY) // 근태 참조
    private List<Attendance> attendance;

//    @OneToOne(fetch = FetchType.LAZY)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentStatus employmentStatus; // 고용 상태

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType; // 고용 유형

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private LocalDate hireDate; // 고용일

    @Column(nullable = false)
    private boolean isHouseholdHead; // 세대주 여부
}