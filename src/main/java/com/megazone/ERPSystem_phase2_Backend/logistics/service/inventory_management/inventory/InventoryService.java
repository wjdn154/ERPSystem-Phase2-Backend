package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryResponseDTO> getAllInventories();

    List<InventoryResponseDTO> getInventoriesByLocationId(Long locationId);

    InventoryResponseDTO createInventory(InventoryRequestDTO requestDTO);

    List<InventoryResponseDTO> getInventoriesByWarehouseId(Long warehouseId);
}
