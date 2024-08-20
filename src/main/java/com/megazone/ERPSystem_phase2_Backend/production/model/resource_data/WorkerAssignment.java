package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private String shift; // 배치된 작업자의 근무 교대조 (예: 주간, 야간)
}