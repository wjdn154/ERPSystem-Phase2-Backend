package com.megazone.ERPSystem_phase2_Backend.logistics.repository.warehouse_registration;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long>, WarehouseRepositoryCustom {
}
