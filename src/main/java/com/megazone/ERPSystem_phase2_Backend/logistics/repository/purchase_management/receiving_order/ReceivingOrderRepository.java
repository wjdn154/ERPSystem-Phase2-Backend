package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivingOrderRepository extends JpaRepository<ReceivingOrder, Long>, ReceivingOrderRepositoryCustom {
}
