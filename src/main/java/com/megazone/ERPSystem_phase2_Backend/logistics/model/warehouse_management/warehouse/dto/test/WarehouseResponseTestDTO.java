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

    public WarehouseResponseTestDTO(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.code = warehouse.getCode();
        this.name = warehouse.getName();
        this.warehouseType = warehouse.getWarehouseType();

        if (warehouse.getWarehouseType() == WarehouseType.FACTORY && warehouse.getProcessDetail() != null) {
            this.productionProcess = new ProductionProcessDTO(warehouse.getProcessDetail());
        }

        this.hierarchyGroups = warehouse.getWarehouseHierarchyGroup()
                .stream()
                .map(whg -> new WarehouseHierarchyGroupDTO(whg.getHierarchyGroup()))
                .collect(Collectors.toList());

        this.isActive = warehouse.getIsActive();
    }
}
