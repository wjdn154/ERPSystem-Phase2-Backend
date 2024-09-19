package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHierarchyGroupDTO {
    private Long id;
    private String hierarchyGroupCode;
    private String hierarchyGroupName;
    private Long parentGroupId;
    private Boolean isActive;
}
