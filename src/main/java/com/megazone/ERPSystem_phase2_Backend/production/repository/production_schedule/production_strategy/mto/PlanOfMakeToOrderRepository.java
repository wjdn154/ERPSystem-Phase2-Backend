package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.production_strategy.mto;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanOfMakeToOrderRepository extends JpaRepository<PlanOfMakeToOrder, Long>, PlanOfMakeToOrderRepositoryCustom {
}
