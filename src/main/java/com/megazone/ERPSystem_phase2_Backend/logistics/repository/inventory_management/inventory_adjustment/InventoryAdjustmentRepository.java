package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAdjustmentRepository extends JpaRepository<InventoryAdjustment, Long>, InventoryAdjustmentRepositoryCustom {
}
