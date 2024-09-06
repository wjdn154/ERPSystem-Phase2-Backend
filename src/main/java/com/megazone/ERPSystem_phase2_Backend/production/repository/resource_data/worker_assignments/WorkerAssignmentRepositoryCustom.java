package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker_assignments;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface WorkerAssignmentRepositoryCustom {
    List<WorkerAssignmentRepository> findWorkerCountByWorkcenter();

    Collection<Object> findByWorkcenterCode(String workcenterCode);

    List<WorkerAssignment> findAssignmentsByWorkerAndWorkOrderAndDate(Long workerId, Long workOrderId, LocalDate assignmentDate);

    boolean existsByWorkerIdAndAssignmentDate(Long workerId, LocalDate date);

}
