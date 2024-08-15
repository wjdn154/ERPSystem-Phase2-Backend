package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//  직원의 출퇴근 기록을 저장

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_Id", nullable=false)
    private Employee employee; // 사원 참조

    @Column(nullable = false)
    private Date date;

}
