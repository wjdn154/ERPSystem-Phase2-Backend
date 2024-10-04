package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryAdjustmentResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionResponseListDTO;

import java.time.LocalDate;
import java.util.List;

public interface InventoryInspectionService {


    List<InventoryInspectionResponseListDTO> getInspectionsByDateRange(LocalDate startDate, LocalDate endDate);

    InventoryInspectionResponseDTO createInventoryInspection(InventoryInspectionRequestDTO requestDTO);

    InventoryInspectionResponseDTO updateInventoryInspection(Long id, InventoryInspectionRequestDTO updateDTO);

    InventoryInspectionResponseDTO getInspectionById(Long id);

    void deleteInspectionById(Long id);

    InventoryAdjustmentResponseDTO adjustInventoryByInspection(Long id);
}
