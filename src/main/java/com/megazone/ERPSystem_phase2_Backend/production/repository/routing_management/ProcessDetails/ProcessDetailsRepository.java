package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessDetailsRepository extends JpaRepository<ProcessDetails, Long>, ProcessDetailsRepositoryCustom {
}
