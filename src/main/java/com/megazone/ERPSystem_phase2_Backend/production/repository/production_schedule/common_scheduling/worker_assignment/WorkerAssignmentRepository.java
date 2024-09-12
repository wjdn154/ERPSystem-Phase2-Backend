package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkerAssignmentRepository extends JpaRepository<WorkerAssignment, Long>, WorkerAssignmentRepositoryCustom {
    List<WorkerAssignment> findByAssignmentDate(LocalDate currentDate);

    List<WorkerAssignment> findByAssignmentDateBetween(LocalDate startOfMonth, LocalDate endOfMonth);


    Optional<WorkerAssignment> findByWorkerIdAndAssignmentDate(Long workerId, LocalDate assignmentDate);
    List<WorkerAssignment> findByAssignmentDateAndShiftTypeId(LocalDate currentDate, Long shiftTypeId);

    List<WorkerAssignment> findByProductionOrderIdAndShiftTypeId(Long productionOrderId, Long shiftTypeId);

    List<WorkerAssignment> findByProductionOrderId(Long productionOrderId);

    List<WorkerAssignment> findByWorkerIdAndShiftTypeId(Long workerId, Long shiftTypeId);

    List<WorkerAssignment> findByWorkerId(Long workerId);
}
