package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.shipping_order_details;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrderDetail.ShippingOrderDetailResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShippingOrderDetailService {
    List<ShippingOrderDetailResponseDTO> getShippingOrderDetailsByDateRange(LocalDate startDate, LocalDate endDate);
}
