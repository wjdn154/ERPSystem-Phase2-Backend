package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.WarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        return warehouseRepository.findAll().stream()
                .map(warehouse -> new WarehouseDTO(
                        warehouse.getId(),
                        warehouse.getCode(),
                        warehouse.getName(),
                        warehouse.getWarehouseType(),
                        warehouse.getProductionProcess(),
                        warehouse.getIsActive()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseDetailDTO getWarehouseDetail(Long id) {
        return warehouseRepository.getWarehouseDetail(id);
    }



    @Override
    public Optional<WarehouseDetailDTO> saveWarehouse(WarehouseDetailDTO dto) {
        Warehouse createWarehouse = new Warehouse();

        warehouseRepository.findByCode(dto.getCode())
                        .ifPresent(warehouse -> {
                            throw new RuntimeException("이미 존재하는 코드입니다: " + dto.getCode());
                        });

        createWarehouse.setId(dto.getId());
        createWarehouse.setCode(dto.getCode());
        createWarehouse.setName(dto.getName());
        createWarehouse.setWarehouseType(dto.getWarehouseType());
        createWarehouse.setProductionProcess(dto.getProductionProcess());
        createWarehouse.setIsActive(dto.getIsActive());

        List<WarehouseHierarchyGroup> warehouseHierarchyGroups = dto.getHierarchyGroupList().stream()
                .map(hierarchyGroupResponseDTO -> {
                    HierarchyGroup hierarchyGroup = hierarchyGroupRepository.findById(hierarchyGroupResponseDTO.getId())
                            .orElseThrow(() -> new RuntimeException("존재하지 않는 계층 그룹입니다: " + hierarchyGroupResponseDTO.getId()));

                    WarehouseHierarchyGroup warehouseHierarchyGroup = new WarehouseHierarchyGroup();
                    warehouseHierarchyGroup.setWarehouse(createWarehouse);
                    warehouseHierarchyGroup.setHierarchyGroup(hierarchyGroup);

                    return warehouseHierarchyGroup;
                })
                .collect(Collectors.toList());

        createWarehouse.setWarehouseHierarchyGroup(warehouseHierarchyGroups);

        Warehouse savedWarehouse = warehouseRepository.save(createWarehouse);

        WarehouseDetailDTO warehouseDetailDTO = new WarehouseDetailDTO(
                savedWarehouse.getId(),
                savedWarehouse.getCode(),
                savedWarehouse.getName(),
                savedWarehouse.getWarehouseType(),
                savedWarehouse.getProductionProcess(),
                savedWarehouse.getWarehouseHierarchyGroup().stream()
                        .map(warehouseHierarchyGroup -> new HierarchyGroupResponseDTO(
                                warehouseHierarchyGroup.getId(),
                                warehouseHierarchyGroup.getHierarchyGroup().getHierarchyGroupCode(),
                                warehouseHierarchyGroup.getHierarchyGroup().getHierarchyGroupName(),
                                warehouseHierarchyGroup.getHierarchyGroup().getIsActive(),
                                warehouseHierarchyGroup.getHierarchyGroup().getParentGroup() != null ? warehouseHierarchyGroup.getHierarchyGroup().getParentGroup().getId() : null,
                                warehouseHierarchyGroup.getHierarchyGroup().getParentGroup() != null ? warehouseHierarchyGroup.getHierarchyGroup().getHierarchyGroupName() : null,
                                warehouseHierarchyGroup.getHierarchyGroup().getChildGroup().stream()
                                        .map(hierarchyGroup -> new HierarchyGroupResponseDTO(
                                                hierarchyGroup.getId(),
                                                hierarchyGroup.getHierarchyGroupCode(),
                                                hierarchyGroup.getHierarchyGroupName(),
                                                hierarchyGroup.getIsActive(),
                                                hierarchyGroup.getParentGroup() != null ? hierarchyGroup.getParentGroup().getId() : null,
                                                hierarchyGroup.getParentGroup() != null ? hierarchyGroup.getParentGroup().getHierarchyGroupName() : null,
                                                null
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList()),
                savedWarehouse.getIsActive()
        );

        return Optional.of(warehouseDetailDTO);
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

