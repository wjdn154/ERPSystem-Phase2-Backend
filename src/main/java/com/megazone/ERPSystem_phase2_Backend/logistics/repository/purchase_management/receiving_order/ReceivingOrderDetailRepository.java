package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivingOrderDetailRepository extends JpaRepository<ReceivingOrderDetail, Long>, ReceivingOrderDetailRepositoryCustom {
}
