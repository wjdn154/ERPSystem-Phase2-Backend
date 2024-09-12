package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "common_scheduling_worker_assignment")
@Table(name = "common_scheduling_worker_assignment", indexes = {
        @Index(name = "idx_assignment_date", columnList = "assignmentDate")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workcenter_id", nullable = false)
    private Workcenter workcenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate assignmentDate; // 배치 날짜

    @ManyToOne
    @JoinColumn(name = "shift_type_id", nullable = false)
    private ShiftType shiftType;  // 교대 근무 유형을 엔티티로 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private ProductionOrder productionOrder;

}
