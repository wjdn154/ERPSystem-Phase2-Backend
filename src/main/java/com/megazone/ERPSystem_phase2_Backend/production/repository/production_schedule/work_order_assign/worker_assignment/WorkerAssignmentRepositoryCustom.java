package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkerAssignmentRepositoryCustom {
    List<WorkerAssignment> findWorkerCountByWorkcenter();

    Optional<WorkerAssignment> findByWorkcenterCode(String workcenterCode);

    List<WorkerAssignment> findAssignmentsByWorkerAndWorkOrderAndDate(Long workerId, Long workOrderId, LocalDate assignmentDate);

    boolean existsByWorkerIdAndAssignmentDate(Long workerId, LocalDate date);

    List<WorkerAssignment> getWorkerAssignments(String workcenterCode, Optional<LocalDate> optionalDate);
    List<WorkerAssignment> findWorkerAssignmentsByWorkcenterIdAndDate(String workcenterCode, LocalDate date);

}