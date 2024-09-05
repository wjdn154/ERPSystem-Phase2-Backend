package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker_assignments;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerAssignmentsRepository extends JpaRepository<WorkerAssignment, Long>, WorkerAssignmentRepositoryCustom {
}
