package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "work_report_work_weekly_report")
@Table(name = "work_report_work_weekly_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkWeeklyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String title; // 주간보고제목

    @Column(nullable = false)
    private String summary; // 주간보고요약

    @Column(nullable = false)
    private Double DefectRate; // 불량률 (자동계산)

    @OneToMany(mappedBy = "workWeeklyReport")
    private List<WorkDailyReport> workDailyReports;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_monthly_report_id")
    private WorkMonthlyReport workMonthlyReport;

    @Column(nullable = true)
    private String remarks; // 비고
}
