package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>, WorkerRepositoryCustom {
    Optional<Worker> findById(Long workerId);

}
