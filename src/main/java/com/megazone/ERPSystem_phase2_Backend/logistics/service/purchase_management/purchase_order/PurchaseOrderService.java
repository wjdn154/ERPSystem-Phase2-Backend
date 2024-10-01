package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {
    List<PurchaseOrderResponseDto> findAllPurchaseOrders();

    Optional<PurchaseOrderResponseDetailDto> findPurchaseOrderDetailById(Long id);

    PurchaseOrderResponseDetailDto createPurchaseOrder(PurchaseOrderCreateDto createDto);

    PurchaseOrderResponseDetailDto updatePurchaseOrder(Long id, PurchaseOrderCreateDto updateDto);

    String deletePurchaseOrder(Long id);
}
