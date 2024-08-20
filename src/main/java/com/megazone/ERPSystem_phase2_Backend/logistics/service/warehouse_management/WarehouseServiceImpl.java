package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final HierarchyGroupRepository hierarchyGroupRepository;


    /**
     * 모든 창고 정보를 가져옴
     * @return 모든 창고 정보를 담은 WarehouseDTO 객체를 반환
     */
    @Override
    public List<WarehouseDTO> findAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();

        List<WarehouseDTO> warehouseDTOS = warehouses.stream()
                .map(warehouse -> new WarehouseDTO(
                        warehouse.getCode(),
                        warehouse.getName(),
                        warehouse.getWarehouseType(),
                        warehouse.getProductionProcess(),
                        warehouse.getIsActive()
                ))
                .collect(Collectors.toList());

        return warehouseDTOS;
    }

    @Override
    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse) {

        Warehouse existingWarehouse = warehouseRepository.findById(warehouse.getId()).orElseThrow(() -> new RuntimeException("아이디로 창고를 찾을 수 없습니다: " + warehouse.getId()));

        existingWarehouse.setCode(warehouse.getCode());
        existingWarehouse.setName(warehouse.getName());
        existingWarehouse.setProductionProcess(warehouse.getProductionProcess());
        existingWarehouse.setIsActive(warehouse.getIsActive());

        if (warehouse.getWarehouseType() != null) existingWarehouse.setWarehouseType(warehouse.getWarehouseType());
        if (warehouse.getAddress() != null) existingWarehouse.setAddress(warehouse.getAddress());

        return warehouseRepository.save(existingWarehouse);
    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("아이디로 창고를 찾을 수 없습니다: " + warehouseId));
        warehouseRepository.delete(warehouse);
    }
}