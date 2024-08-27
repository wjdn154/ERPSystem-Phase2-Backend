package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

// 사원 엔티티

@Data
@Entity(name = "employee")
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = "department, position, jobTitle")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="employee_number",nullable = false,unique = true)
    private String employeeNumber; // 사원 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false) // 부서 참조
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false) // 직위 참조
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobTitle_id", nullable = false) // 직책 참조
    private JobTitle jobTitle;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 성과 평가 참조
    private List<Performance> performance;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true) // 발령 참조
    private List<Transfer> transfer;

    @OneToOne(mappedBy = "employee",fetch = FetchType.LAZY, cascade = CascadeType.ALL , orphanRemoval = true) // Users 랑 1대1 참조
    private Users users;

    @OneToOne(cascade = CascadeType.ALL) // 계좌번호랑 1대 1참조
    @JoinColumn(name = "bankaccount_id")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 휴가 참조
    private List<Leaves> leaves;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 근태 참조
    private List<Attendance> attendance;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Salary> salaries;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "allowance_Id", nullable = false)
//    private Allowance allowance;

    @Column(nullable = false)
    private String firstName; // 성

    @Column(nullable = false)
    private String lastName; // 이름

    @Column(nullable = false)
    private LocalDate dateOfBirth; // 생년월일

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

    @Column
    private String profilePicture; // 프로필 사진
}