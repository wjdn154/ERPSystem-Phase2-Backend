package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.orders;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Orders;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<Orders> findBySearch(SearchDTO dto);
}
