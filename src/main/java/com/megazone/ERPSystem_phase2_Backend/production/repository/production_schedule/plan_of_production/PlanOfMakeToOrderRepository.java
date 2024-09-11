package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.plan_of_production;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.plan_of_production.PlanOfMakeToOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanOfMakeToOrderRepository extends JpaRepository<PlanOfMakeToOrder, Long> {
}
