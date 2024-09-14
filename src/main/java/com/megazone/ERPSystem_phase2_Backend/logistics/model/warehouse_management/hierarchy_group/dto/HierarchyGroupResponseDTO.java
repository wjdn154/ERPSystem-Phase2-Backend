package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyGroupResponseDTO {
    private Long id;
    private String hierarchyGroupCode;
    private String hierarchyGroupName;
    private Boolean isActive;
    private Long parentGroupId;
    private String parentGroupName;
    private List<HierarchyGroupResponseDTO> childGroups;
}

