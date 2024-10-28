package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.shipping_order;



import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface ShippingOrderService {
    List<ShippingOrderResponseDto> findAllShippingOrders(SearchDTO dto);

    Optional<ShippingOrderResponseDetailDto> findShippingOrderDetailById(Long id);

    ShippingOrderResponseDetailDto createShippingOrder(ShippingOrderCreateDto createDto);

    ShippingOrderResponseDetailDto updateShippingOrder(Long id, ShippingOrderCreateDto updateDto);

    String deleteShippingOrder(Long id);
}
