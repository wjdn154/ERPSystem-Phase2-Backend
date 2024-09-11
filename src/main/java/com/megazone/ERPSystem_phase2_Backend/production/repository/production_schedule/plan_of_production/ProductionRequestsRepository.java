package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.plan_of_production;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.plan_of_production.ProductionRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRequestsRepository extends JpaRepository<ProductionRequests, Long> {
}
