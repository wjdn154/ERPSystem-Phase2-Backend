package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.AdjustmentRequestDTO;

public interface InventoryAdjustmentService {
    void adjustInventory(AdjustmentRequestDTO adjustmentRequestDTO);
}
