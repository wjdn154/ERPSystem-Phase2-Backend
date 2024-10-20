package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionStatusResponseListDTO;

import java.time.LocalDate;
import java.util.List;

public interface InventoryInspectionDetailService {
    List<InventoryInspectionStatusResponseListDTO> getInspectionStatusByDateRange(LocalDate startDate, LocalDate endDate);
}
