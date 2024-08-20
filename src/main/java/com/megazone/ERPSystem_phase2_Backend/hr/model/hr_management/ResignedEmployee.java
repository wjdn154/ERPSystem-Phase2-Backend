package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

// 퇴사자 정보 테이블

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ResignedEmployee {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id", nullable=false)
    private Department department;  // 부서 참조

    @Column(nullable = false)
    private String reason; // 퇴사 사유 ( 개인 사유, 이직, 건강 문제 등 )

    @Column(nullable = false)
    private LocalDate resignationDate; // 퇴사일

    @Column
    private String remarks; // 비고 ( 추가적인 메모나 비고 사항 )
}
