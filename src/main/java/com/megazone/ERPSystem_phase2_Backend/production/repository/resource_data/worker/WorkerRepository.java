package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker;

import java.util.Optional;

public interface WorkerRepository {
    Optional<Object> findById(Long workerId);

}
