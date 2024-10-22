package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling;


import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToStock;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 작업지시 엔티티
 */

@Entity(name = "common_scheduling_production_order")
@Table(name = "common_scheduling_production_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false)
    private String name; // 작업지시명

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "plan_of_make_to_order_id", nullable = true)
//    private PlanOfMakeToOrder planOfMakeToOrder;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "plan_of_make_to_stock_id", nullable = true)
//    private PlanOfMakeToStock planOfMakeToStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mps_id", nullable = false)
    private Mps mps; // 연관된 MPS

    @OneToMany(mappedBy = "productionOrder")
    @Column(nullable = false)
    private List<WorkerAssignment> workerAssignments;

    @OneToMany(mappedBy = "productionOrder", orphanRemoval = true)
    private List<WorkPerformance> workPerformances; // 여러 작업 실적과의 연관 관계

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

    @Column(nullable = true)
    private Boolean confirmed; // 확정 여부

    @Column(nullable = false)
    @Builder.Default
    private Boolean closed = false; // 마감 여부 (기본값: 미마감)

    @Column(nullable = false)
    private LocalDateTime startDateTime; // 작업 시작 날짜 및 시간

    @Column(nullable = false)
    private LocalDateTime endDateTime; // 작업 종료 날짜 및 시간
}
