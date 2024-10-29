package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order_details;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrderDetail;

import java.time.LocalDate;
import java.util.List;

public interface ShippingOrderDetailRepositoryCustom {

    List<ShippingOrderDetail> findDetailsByOrderDateRange(LocalDate startDate, LocalDate endDate);
}
