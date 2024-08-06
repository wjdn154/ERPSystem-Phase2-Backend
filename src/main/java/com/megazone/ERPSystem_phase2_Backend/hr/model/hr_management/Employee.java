package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// ��� �⺻ ���� ���� ���̺�

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_Id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_Id", nullable = false)
    private Position position;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "salary_Id", nullable = false)
//    private Salary salary;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "allowance_Id", nullable = false)
//    private Allowance allowance;

    @Column(nullable = false)
    private String firstName; // ��

    @Column(nullable = false)
    private String lastName; // �̸�

    @Column(nullable = false)
    private Date dateOfBirth; // �������

    @Column(nullable = false)
    private String phoneNumber; // �޴��� ��ȣ

    @Column(nullable = false)
    private String email; // �̸���

    @Column(nullable = false)
    private String address; // �ּ�

    @Column(nullable = false)
    private LocalDate hireDate; // �����
}
