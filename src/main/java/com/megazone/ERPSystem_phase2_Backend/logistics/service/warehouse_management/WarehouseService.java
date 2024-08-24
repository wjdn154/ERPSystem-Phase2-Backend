package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.UpdateWarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {

    List<WarehouseResponseDTO> findAllWarehouses();

    WarehouseDetailDTO getWarehouseDetail(Long id);

    Optional<WarehouseDetailDTO> saveWarehouse(WarehouseDetailDTO dto);

    Optional<UpdateWarehouseDTO> updateWarehouse(Long id, UpdateWarehouseDTO dto);

    String deleteWarehouse(Long id);

}
//