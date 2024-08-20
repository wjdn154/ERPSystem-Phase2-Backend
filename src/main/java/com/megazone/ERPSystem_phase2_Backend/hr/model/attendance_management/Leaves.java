package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.LeaveStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 직원의 휴가 신청 및 기록을 저장

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee; // 사원 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="leavestype_id", nullable = false) // 휴가 유형 참조
    private LeavesType leavesType;

    @Column(nullable = false)
    private Date startDate; // 휴가 시작일

    @Column(nullable = false)
    private Date endDate; // 휴가 종료일

    @Column(nullable = false)
    private String reason; // 휴가 사유

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status;
}