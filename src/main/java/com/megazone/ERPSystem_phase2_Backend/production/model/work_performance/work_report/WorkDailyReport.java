package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**생상량 및 작업 실적
 * 품질 검사 결과, 불량 처리 내용, 작업 비용 분석, 작업 효율 및 개선 사항
 * 품목 코드, 작업지시 코드, 자재 코드, 불량유형 코드,
 * */
@Entity(name = "work_report_work_daily_report")
@Table(name = "work_report_work_daily_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자
    
    @Column(nullable = false)
    private String workDailyReportCode;   //일별 보고서 코드

    @Column(nullable = false)
    private String title; // 일간보고제목

    @Column(nullable = false)
    private String summary; // 일간보고요약

    @Column(nullable = false)
    private Boolean isInspected; // 실적검사여부

    @OneToMany(mappedBy = "workDailyReport")
    private List<WorkPerformance> workPerformances;     // WorkPerformance 엔티티 List

    @Column(nullable = false)
    private BigDecimal dailyProductionQuantity; // BigDecimal 일생산량

    @Column(nullable = false)
    private BigDecimal dailyDefectiveQuantity; // 일 집계 불량수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_monthly_report_id")
    private WorkMonthlyReport workMonthlyReport;
}
