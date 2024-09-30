package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDto;

import java.util.List;

public interface PurchaseOrderService {
    List<PurchaseOrderResponseDto> findAllPurchaseOrders();
}
