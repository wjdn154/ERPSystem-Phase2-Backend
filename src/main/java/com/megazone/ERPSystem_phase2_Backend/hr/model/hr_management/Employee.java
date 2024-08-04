package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Employee {
    @Id
    private Long id;

//    @JoinColumn(nullable = false)
//    private PositionId positionId;
//
//    @JoinColumn(nullable = false)
//    private SalaryId salaryId;
//
//    @JoinColumn(nullable = false)
//    private AllowanceId allowanceId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate hireDate;

//    @Column(nullable = false)
//    private EmployeementType employeementType;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Department departmentId;

}
