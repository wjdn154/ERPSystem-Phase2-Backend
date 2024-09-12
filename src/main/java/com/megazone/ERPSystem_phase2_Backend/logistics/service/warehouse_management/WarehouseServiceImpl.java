package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.WarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseListResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseRequestTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse_hierarchy_group.WarehouseHierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
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
    public List<WarehouseListResponseDTO> getWarehouseListByCompany(Long companyId) {
        List<Warehouse> warehouses = warehouseRepository.findAllByCompanyId(companyId);
        return warehouses.stream()
                .map(WarehouseListResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseResponseTestDTO getWarehouseDetailTest(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("창고를 찾을 수 없습니다."));
        return new WarehouseResponseTestDTO(warehouse);
    }

    @Override
    public WarehouseResponseTestDTO saveTestWarehouse(WarehouseRequestTestDTO warehouseRequestTestDTO, Long companyId) {
        Company company= new Company();
        company.setId(companyId);

        ProcessDetails processDetails = warehouseRequestTestDTO.getProcessDetailsId() != null ?
                processDetailsRepository.findById(warehouseRequestTestDTO.getProcessDetailsId())
                        .orElseThrow(() -> new RuntimeException("공정 정보를 찾을 수 없습니다.")) : null;

        List<WarehouseHierarchyGroup> hierarchyGroups = null;
        if (warehouseRequestTestDTO.getHierarchyGroups() != null && !warehouseRequestTestDTO.getHierarchyGroups().isEmpty()) {
            hierarchyGroups = warehouseRequestTestDTO.getHierarchyGroups().stream()
                    .map(dto -> warehouseHierarchyGroupRepository.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("계층 그룹을 찾을 수 없습니다.")))
                    .collect(Collectors.toList());
        }

        Warehouse warehouse = warehouseRequestTestDTO.mapToEntity(processDetails, hierarchyGroups);
        warehouse.setCompany(company);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return new WarehouseResponseTestDTO(savedWarehouse);
    }

    @Override
    public WarehouseResponseTestDTO updateTestWarehouse(Long id, WarehouseRequestTestDTO dto) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 창고를 찾을 수 없습니다."));

        ProcessDetails processDetails = dto.getProcessDetailsId() != null ?
                processDetailsRepository.findById(dto.getProcessDetailsId())
                        .orElseThrow(() -> new RuntimeException("공정 정보를 찾을 수 없습니다.")) : null;

        List<WarehouseHierarchyGroup> hierarchyGroups = null;
        if (dto.getHierarchyGroups() != null && !dto.getHierarchyGroups().isEmpty()) {
            hierarchyGroups = dto.getHierarchyGroups().stream()
                    .map(groups -> warehouseHierarchyGroupRepository.findById(groups.getId())
                            .orElseThrow(() -> new RuntimeException("계층 그룹을 찾을 수 없습니다.")))
                    .collect(Collectors.toList());
        }

        warehouse.setCode(dto.getCode());
        warehouse.setName(dto.getName());
        warehouse.setWarehouseType(dto.getWarehouseType());
        warehouse.setIsActive(dto.getIsActive());
        warehouse.setProcessDetail(processDetails);

        if (hierarchyGroups != null) {
            warehouse.setWarehouseHierarchyGroup(hierarchyGroups);
        }

        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);
        return new WarehouseResponseTestDTO(updatedWarehouse);
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

