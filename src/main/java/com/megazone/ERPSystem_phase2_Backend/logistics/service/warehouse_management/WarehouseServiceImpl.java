package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.WarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseRequestTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse_hierarchy_group.WarehouseHierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
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
    private final WarehouseHierarchyGroupRepository warehouseHierarchyGroupRepository;
    private final ProcessDetailsRepository processDetailsRepository;


    @Override
    public List<WarehouseResponseListDTO> getWarehouseList() {
        return warehouseRepository.findWarehouseList();
    }

    @Override
    public WarehouseResponseTestDTO getWarehouseDetail(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 창고를 찾을 수 없습니다."));

        return WarehouseResponseTestDTO.mapToDTO(warehouse);
    }

    @Override
    public WarehouseResponseTestDTO createWarehouse(WarehouseRequestTestDTO requestDTO) {
        ProcessDetails processDetail = null;
        if (requestDTO.getWarehouseType() == WarehouseType.FACTORY && requestDTO.getProcessDetailsId() != null) {
            processDetail = processDetailsRepository.findById(requestDTO.getProcessDetailsId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 생산 공정입니다."));
        }

        List<WarehouseHierarchyGroup> hierarchyGroups = requestDTO.getHierarchyGroups().stream()
                .map(dto -> warehouseHierarchyGroupRepository.findById(dto.getId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계층 그룹입니다.")))
                .collect(Collectors.toList());

        Warehouse newWarehouse = requestDTO.mapToEntity(processDetail, hierarchyGroups);

        Warehouse savedWarehouse = warehouseRepository.save(newWarehouse);

        return WarehouseResponseTestDTO.mapToDTO(savedWarehouse);
    }

    @Override
    public WarehouseResponseTestDTO updateWarehouse(Long warehouseId, WarehouseRequestTestDTO requestDTO) {
        Warehouse existingWarehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 창고를 찾을 수 없습니다."));

        List<WarehouseHierarchyGroup> updatedHierarchyGroups = requestDTO.getHierarchyGroups().stream()
                .map(dto -> {
                    HierarchyGroup hierarchyGroup = hierarchyGroupRepository.findById(dto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 계층 그룹을 찾을 수 없습니다."));

                    return WarehouseHierarchyGroup.builder()
                            .warehouse(existingWarehouse)
                            .hierarchyGroup(hierarchyGroup)
                            .build();
                })
                .collect(Collectors.toList());

        ProcessDetails processDetails = null;
        if (requestDTO.getWarehouseType() == WarehouseType.FACTORY && requestDTO.getProcessDetailsId() != null) {
            processDetails = processDetailsRepository.findById(requestDTO.getProcessDetailsId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 생산 공정을 찾을 수 없습니다."));
        }

        Warehouse updatedWarehouse = Warehouse.builder()
                .id(existingWarehouse.getId())
                .code(requestDTO.getCode())
                .name(requestDTO.getName())
                .warehouseType(requestDTO.getWarehouseType())
                .isActive(requestDTO.getIsActive())
                .processDetail(processDetails)
                .warehouseHierarchyGroup(updatedHierarchyGroups)
                .build();

        Warehouse savedWarehouse = warehouseRepository.save(updatedWarehouse);

        return WarehouseResponseTestDTO.mapToDTO(savedWarehouse);
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

