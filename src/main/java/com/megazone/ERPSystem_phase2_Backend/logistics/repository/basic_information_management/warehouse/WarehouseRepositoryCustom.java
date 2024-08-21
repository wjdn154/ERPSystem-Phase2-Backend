package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;

import java.util.List;

public interface WarehouseRepositoryCustom {
    List<WarehouseDTO> findAllWarehouse();

    WarehouseDetailDTO getWarehouseDetail(Long id);
}
