package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryCustom {
    boolean existsByWarehouseLocationId(Long warehouseLocationId);

    boolean existsByWarehouse(Warehouse warehouse);

}
