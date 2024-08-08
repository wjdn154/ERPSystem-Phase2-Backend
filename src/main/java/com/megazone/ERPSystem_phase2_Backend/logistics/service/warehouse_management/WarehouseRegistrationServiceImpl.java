package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.HierarchyGroup.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseRegistrationServiceImpl implements WarehouseRegistrationService {

    private final WarehouseRepository warehouseRepository;
    private final HierarchyGroupRepository hierarchyGroupRepository;

    @Override
    public Warehouse saveWarehouse(Warehouse warehouse) {
        return null;
    }

    @Override
    public Warehouse updateAccount(Warehouse warehouse) {
        return null;
    }

    @Override
    public void deleteWarehouse(Long warehouseId) {

    }
}
