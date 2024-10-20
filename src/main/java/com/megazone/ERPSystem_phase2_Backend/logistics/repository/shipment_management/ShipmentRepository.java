package com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.Shipment;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long>, ShipmentRepositoryCustom {
    boolean existsByWarehouse(Warehouse warehouse);
}
