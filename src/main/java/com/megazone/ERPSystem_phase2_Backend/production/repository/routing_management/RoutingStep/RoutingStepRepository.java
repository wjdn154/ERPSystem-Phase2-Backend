package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.RoutingStep;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStepId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutingStepRepository extends JpaRepository<RoutingStep, RoutingStepId>, RoutingStepRepositoryCustom {
}
