package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;

import java.util.Optional;

public interface ProcessDetailsRepositoryCustom {

    Optional<ProcessDetails> findByCode(String code);
}
