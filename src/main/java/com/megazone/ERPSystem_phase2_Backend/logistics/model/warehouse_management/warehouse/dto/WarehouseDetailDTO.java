package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDetailDTO {
    private Long id;
    private String code;
    private String name;
    private WarehouseType warehouseType;
    private String productionProcess;
    private List<HierarchyGroupResponseDTO> hierarchyGroupList;
    private Boolean isActive;
}
