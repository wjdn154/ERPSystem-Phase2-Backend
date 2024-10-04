package com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.Shipment;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentRepositoryCustom {

    Long findMaxShipmentNumberByDate(LocalDate shipmentDate);

    List<Shipment> findShipmentListByDateRange(LocalDate startDate, LocalDate endDate);

    Long findNextShipmentNumber(LocalDate shipmentDate);
}
