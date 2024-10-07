package com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.Shipment;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.ShipmentProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentProductRepository extends JpaRepository<ShipmentProduct, Long> {
    void deleteAllByShipment(Shipment existingShipment);
}
