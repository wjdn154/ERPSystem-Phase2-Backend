package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;

import java.util.Optional;

public interface WorkerRepository {
    Optional<Worker> findById(Long workerId);

}
