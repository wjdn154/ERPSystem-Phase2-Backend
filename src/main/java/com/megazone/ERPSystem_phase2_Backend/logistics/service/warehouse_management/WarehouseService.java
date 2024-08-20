package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;

import java.util.List;

public interface WarehouseService {

    List<WarehouseDTO> findAllWarehouses();

    WarehouseDetailDTO getWarehouseDetail(Long id);

    Warehouse saveWarehouse(Warehouse warehouse);

    Warehouse updateWarehouse(Warehouse warehouse);

    void deleteWarehouse(Long warehouseId);

}
//