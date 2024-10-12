package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.receiving_order;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.ReceivingOrderCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.ReceivingOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.ReceivingOrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface ReceivingOrderService {
    List<ReceivingOrderResponseDto> findAllReceivingOrders();

    Optional<ReceivingOrderResponseDetailDto> findReceivingOrderDetailById(Long id);

    ReceivingOrderResponseDetailDto createReceivingOrder(ReceivingOrderCreateDto createDto);

    ReceivingOrderResponseDetailDto updatePurchaseOrder(Long id, ReceivingOrderCreateDto updateDto);

    String deleteReceivingOrder(Long id);
}
