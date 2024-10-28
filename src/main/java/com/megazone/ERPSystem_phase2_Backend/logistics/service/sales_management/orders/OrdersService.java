package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.orders;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrdersService {
    List<OrdersResponseDto> findAllOrders(SearchDTO dto);

    Optional<OrdersResponseDetailDto> findOrdersDetailById(Long id);

    OrdersResponseDetailDto createOrders(OrdersCreateDto createDto);

    OrdersResponseDetailDto updateOrders(Long id, OrdersCreateDto updateDto);

    String deleteOrders(Long id);
}
