package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign;


import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.plan_of_production.PlanOfMakeToOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.plan_of_production.PlanOfMakeToStock;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 작업지시 엔티티
 */

@Entity(name = "production_schedule_work_order")
@Table(name = "production_schedule_work_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false)
    private String name; // 작업지시명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_of_make_to_order_id", nullable = true)
    private PlanOfMakeToOrder planOfMakeToOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_of_make_to_stock_id", nullable = true)
    private PlanOfMakeToStock planOfMakeToStock;

    @OneToMany(mappedBy = "workOrder")
    @Column(nullable = false)
    private List<WorkerAssignment> workerAssignments;

    @OneToMany(mappedBy = "workOrder", orphanRemoval = true)
    private List<WorkPerformance> workPerformances; // 여러 작업 실적과의 연관 관계

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

}
