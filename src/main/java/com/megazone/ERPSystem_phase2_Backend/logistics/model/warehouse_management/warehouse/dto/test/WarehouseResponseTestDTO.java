package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseResponseTestDTO {
    private Long id;
    private String code;
    private String name;
    private WarehouseType warehouseType;
    private ProductionProcessDTO productionProcess;
    private List<WarehouseHierarchyGroupDTO> hierarchyGroups; // 계층 그룹 코드만 보여주기
    private Boolean isActive;

    public static WarehouseResponseTestDTO mapToDTO(Warehouse warehouse) {
        WarehouseResponseTestDTO dto = new WarehouseResponseTestDTO();
        dto.setId(warehouse.getId());
        dto.setCode(warehouse.getCode());
        dto.setName(warehouse.getName());
        dto.setWarehouseType(warehouse.getWarehouseType());

        // 생산 공정 정보가 있을 경우만 추가
        if (warehouse.getProcessDetail() != null) {
            dto.setProductionProcess(new ProductionProcessDTO(warehouse.getProcessDetail()));
        }

        // 계층 그룹 정보가 있을 경우만 추가
        dto.setHierarchyGroups(warehouse.getWarehouseHierarchyGroup().stream()
                .map(whg -> new WarehouseHierarchyGroupDTO(whg.getHierarchyGroup()))
                .collect(Collectors.toList()));

        dto.setIsActive(warehouse.getIsActive());
        return dto;
    }
}
