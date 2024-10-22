package com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.ShipmentProduct;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentProductRepositoryCustom {
    List<ShipmentProduct> findShipmentItemsByDateRange(LocalDate startDate, LocalDate endDate);
}
