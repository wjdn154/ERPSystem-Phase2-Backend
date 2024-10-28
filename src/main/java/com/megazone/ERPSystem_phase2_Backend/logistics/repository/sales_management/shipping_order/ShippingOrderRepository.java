package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, Long>, ShippingOrderRepositoryCustom {
}
