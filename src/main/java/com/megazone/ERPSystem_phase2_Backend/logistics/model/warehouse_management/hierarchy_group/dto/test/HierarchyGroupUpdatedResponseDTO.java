package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyGroupUpdatedResponseDTO {
    private Long id;
    private String hierarchyGroupCode;
    private String hierarchyGroupName;
    private Long parentGroupId;
    private Boolean isActive;

    public HierarchyGroupUpdatedResponseDTO(HierarchyGroup group) {
        this.id = group.getId();
        this.hierarchyGroupCode = group.getHierarchyGroupCode();
        this.hierarchyGroupName = group.getHierarchyGroupName();
        this.isActive = group.getIsActive();
        this.parentGroupId = group.getParentGroup() != null ? group.getParentGroup().getId() : null;
    }
}
