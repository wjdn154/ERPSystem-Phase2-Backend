package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkerAssignmentRepository extends JpaRepository<WorkerAssignment, Long>, WorkerAssignmentRepositoryCustom {
    Optional<WorkerAssignment> findByAssignmentDate(LocalDate currentDate);

}
