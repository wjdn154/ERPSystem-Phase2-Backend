package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspection;

import java.time.LocalDate;
import java.util.List;

public interface InventoryInspectionRepositoryCustom {

    List<InventoryInspection> findInspectionsByDateRange(LocalDate startDate, LocalDate endDate);

    Long findMaxInspectionNumberByDate(LocalDate inspectionDate);
}
