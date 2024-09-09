package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.WarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.UpdateWarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse_hierarchy_group.WarehouseHierarchyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final HierarchyGroupRepository hierarchyGroupRepository;
    private final WarehouseHierarchyGroupRepository warehouseHierarchyGroupRepository;


    /**
     * 모든 창고 정보를 가져옴
     *
     * @return 모든 창고 정보를 담은 WarehouseResponseDTO 객체를 반환
     */
    @Override
    public List<WarehouseResponseDTO> findAllWarehouses(Long companyId) {
        return warehouseRepository.findAllByCompanyId(companyId).stream()
                .map(warehouse -> new WarehouseResponseDTO(
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
    @Transactional
    public Optional<UpdateWarehouseDTO> updateWarehouse(Long id, UpdateWarehouseDTO dto) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("창고를 찾을 수 없습니다: " + id));

        warehouse.setName(dto.getName());
        warehouse.setWarehouseType(dto.getWarehouseType());
        warehouse.setProductionProcess(dto.getProductionProcess());

        List<WarehouseHierarchyGroup> existingGroups = warehouse.getWarehouseHierarchyGroup();

        Set<Long> updatedGroupIds = dto.getHierarchyGroupList().stream()
                .map(HierarchyGroupResponseDTO::getId)
                .collect(Collectors.toSet());

        List<WarehouseHierarchyGroup> removedGroup = existingGroups.stream()
                .filter(group -> !updatedGroupIds.contains(group.getHierarchyGroup().getId()))
                .collect(Collectors.toList());

        removedGroup.forEach(warehouseHierarchyGroup -> {
            warehouse.getWarehouseHierarchyGroup().remove(warehouseHierarchyGroup);
            warehouseHierarchyGroupRepository.delete(warehouseHierarchyGroup);
        });

        dto.getHierarchyGroupList().forEach(hierarchyGroupResponseDTO -> {
            boolean exists = existingGroups.stream()
                    .anyMatch(group -> group.getHierarchyGroup().getId().equals(hierarchyGroupResponseDTO.getId()));

            if (!exists) {
                HierarchyGroup hierarchyGroup = hierarchyGroupRepository.findById(hierarchyGroupResponseDTO.getId())
                        .orElseThrow(() -> new RuntimeException("존재하지 않는 계층그룹입니다: " + hierarchyGroupResponseDTO.getId()));

                WarehouseHierarchyGroup newGroup = new WarehouseHierarchyGroup();
                newGroup.setWarehouse(warehouse);
                newGroup.setHierarchyGroup(hierarchyGroup);
                existingGroups.add(newGroup);
            }
        });

        warehouse.setIsActive(dto.getIsActive());

        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        UpdateWarehouseDTO updateWarehouseDTO = new UpdateWarehouseDTO(
                updatedWarehouse.getName(),
                updatedWarehouse.getWarehouseType(),
                updatedWarehouse.getProductionProcess(),
                updatedWarehouse.getWarehouseHierarchyGroup().stream()
                        .map(warehouseHierarchyGroup -> {
                            HierarchyGroup hierarchyGroup = warehouseHierarchyGroup.getHierarchyGroup();
                            return new HierarchyGroupResponseDTO(
                                    hierarchyGroup.getId(),
                                    hierarchyGroup.getHierarchyGroupCode(),
                                    hierarchyGroup.getHierarchyGroupName(),
                                    hierarchyGroup.getIsActive(),
                                    hierarchyGroup.getParentGroup() != null ? hierarchyGroup.getParentGroup().getId() : null,
                                    hierarchyGroup.getParentGroup() != null ? hierarchyGroup.getParentGroup().getHierarchyGroupName() : null,
                                    null
                            );
                        })
                        .collect(Collectors.toList()),
                updatedWarehouse.getIsActive()
        );

        return Optional.of(updateWarehouseDTO);
    }


    @Override
    public String deleteWarehouse(Long id) {
        return warehouseRepository.findById(id)
                .map(warehouse -> {
                    warehouseRepository.delete(warehouse);
                    return warehouse.getName() + "가 삭제되었습니다.";
                })
                .orElse("삭제 실패 : 삭제하려는 창고 ID를 찾을 수 없습니다.");
    }
}

