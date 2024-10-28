package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrder;

import java.util.List;

public interface ShippingOrderRepositoryCustom {
    List<ShippingOrder> findBySearch(SearchDTO dto);
}
