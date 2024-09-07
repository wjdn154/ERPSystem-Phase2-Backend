package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker_assignments;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface WorkerAssignmentRepository extends JpaRepository<WorkerAssignment, Long>, WorkerAssignmentRepositoryCustom {
    Optional<WorkerAssignment> findByAssignmentDate(LocalDate currentDate);

}
