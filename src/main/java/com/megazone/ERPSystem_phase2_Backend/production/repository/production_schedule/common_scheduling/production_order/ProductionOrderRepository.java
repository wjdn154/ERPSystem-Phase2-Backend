package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long>, ProductionOrderRepositoryCustom {
    Optional<ProductionOrder> findByName(String productionOrderName);
    List<ProductionOrder> findByMpsId(Long mpsId);


    List<ProductionOrder> findByWorkcenter(Workcenter workcenter);

    List<ProductionOrder> findByConfirmedFalse();
}
