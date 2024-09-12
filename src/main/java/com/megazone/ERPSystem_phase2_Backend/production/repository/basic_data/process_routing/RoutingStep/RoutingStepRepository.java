package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.RoutingStep;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStepId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutingStepRepository extends JpaRepository<RoutingStep, RoutingStepId>, RoutingStepRepositoryCustom {
}
