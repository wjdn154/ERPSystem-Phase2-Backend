package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseHierarchyGroupDTO {
    private Long id;
    private String code;
    private String name;

    public WarehouseHierarchyGroupDTO(HierarchyGroup hierarchyGroup) {
        this.id = hierarchyGroup.getId();
        this.code = hierarchyGroup.getHierarchyGroupCode();
        this.name = hierarchyGroup.getHierarchyGroupName();
    }
}
