package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Department {
    @Id
    private Long id;

    @Column(nullable = false)
    private String departmentName;

    @Column(nullable = false)
    private String managerId;

    @OneToMany
    private List<Employee> employees;
}
