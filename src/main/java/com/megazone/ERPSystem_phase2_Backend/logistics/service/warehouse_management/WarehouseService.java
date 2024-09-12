package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseListResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseRequestTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;

import java.util.List;

public interface WarehouseService {

    List<WarehouseListResponseDTO> getWarehouseListByCompany(Long companyId);

    WarehouseResponseTestDTO getWarehouseDetailTest(Long id);

    WarehouseResponseTestDTO saveTestWarehouse(WarehouseRequestTestDTO warehouseRequestTestDTO, Long companyId);

    WarehouseResponseTestDTO updateTestWarehouse(Long id, WarehouseRequestTestDTO dto);

    String deleteWarehouse(Long id);

}
