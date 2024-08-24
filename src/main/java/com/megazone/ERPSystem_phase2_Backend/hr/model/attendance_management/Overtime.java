package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 초과 근무 저장 엔티티

@Data
@Entity(name="attendance_overtime")
@Table(name="attendance_overtime")
@AllArgsConstructor
@NoArgsConstructor
public class Overtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate overtimeDate; // 연장 근무 날짜

    @Column(nullable = false)
    private LocalDateTime hours; // 연장 근무 시간
}
