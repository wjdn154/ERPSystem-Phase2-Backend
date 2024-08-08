package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;

public interface WarehouseRegistrationService {
    Warehouse saveWarehouse(Warehouse warehouse);

    Warehouse updateAccount(Warehouse warehouse);

    void deleteWarehouse(Long warehouseId);

}
