package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto.WarehouseLocationRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto.WarehouseLocationResponseDTO;

import java.util.List;

public interface WarehouseLocationService {

    List<WarehouseLocationResponseDTO> getLocationsByWarehouseId(Long warehouseId);

    WarehouseLocationResponseDTO createWarehouseLocation(WarehouseLocationRequestDTO requestDTO);

    WarehouseLocationResponseDTO updateWarehouseLocation(Long id, WarehouseLocationRequestDTO requestDTO);

    void deleteWarehouseLocation(Long id);
}
