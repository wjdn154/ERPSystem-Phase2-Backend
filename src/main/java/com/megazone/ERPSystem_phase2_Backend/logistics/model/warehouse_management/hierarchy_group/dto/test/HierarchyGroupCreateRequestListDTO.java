package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyGroupCreateRequestListDTO {
    private Long parentGroupId;
    private List<HierarchyGroupCreateRequestDTO> hierarchyGroups;
}
