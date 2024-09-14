package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.production_strategy;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanOfMakeToStockRepository extends JpaRepository<PlanOfMakeToStock, Long> {
}
