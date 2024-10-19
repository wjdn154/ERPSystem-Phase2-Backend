package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryCustom {
    boolean existsByWarehouseLocationId(Long warehouseLocationId);

    boolean existsByWarehouse(Warehouse warehouse);
}
