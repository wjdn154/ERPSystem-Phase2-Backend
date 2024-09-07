package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report;


import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "work_report_work_performance")
@Table(name = "work_report_work_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false)
    private String name; // 작업실적명

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_daily_report_id")
    private WorkDailyReport workDailyReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder; // 연관 작업지시

    @Column(nullable = false)
    private String product; // TODO 연관 제품

}
