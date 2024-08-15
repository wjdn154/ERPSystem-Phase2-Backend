package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkMonthlyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String title; // 월간보고제목

    @Column(nullable = false)
    private String summary; // 월간보고요약

    @Column(nullable = false)
    private Double DefectRate; // 불량률

    @Column(nullable = true)
    private String remarks; // 비고

}
