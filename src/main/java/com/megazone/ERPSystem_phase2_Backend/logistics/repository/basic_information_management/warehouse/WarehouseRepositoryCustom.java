package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;

import java.util.List;

public interface WarehouseRepositoryCustom {
    List<WarehouseResponseDTO> findAllWarehouse();

    WarehouseDetailDTO getWarehouseDetail(Long id);
}
