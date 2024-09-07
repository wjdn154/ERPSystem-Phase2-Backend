package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.work_order;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>,  WorkOrderRepositoryCustom {
}
