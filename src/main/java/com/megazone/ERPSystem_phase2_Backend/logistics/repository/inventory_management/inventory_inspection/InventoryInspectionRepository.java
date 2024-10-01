package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryInspectionRepository extends JpaRepository<InventoryInspection, Long>, InventoryInspectionRepositoryCustom {
}
