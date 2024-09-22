package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseRequestTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;

import java.util.List;

public interface WarehouseService {

    List<WarehouseResponseListDTO> getWarehouseList();

    WarehouseResponseTestDTO getWarehouseDetail(Long warehouseId);

    WarehouseResponseTestDTO createWarehouse(WarehouseRequestTestDTO requestDTO);

    WarehouseResponseTestDTO updateWarehouse(Long warehouseId, WarehouseRequestTestDTO requestDTO);

    String deleteWarehouse(Long id);

}
