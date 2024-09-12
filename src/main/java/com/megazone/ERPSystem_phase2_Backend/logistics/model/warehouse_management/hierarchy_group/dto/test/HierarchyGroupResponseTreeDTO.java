package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyGroupResponseTreeDTO {
    private Long id;
    private String hierarchyGroupCode;
    private String hierarchyGroupName;
    private Boolean isActive;
    private List<HierarchyGroupResponseTreeDTO> childGroups;

    public HierarchyGroupResponseTreeDTO(HierarchyGroup hierarchyGroup) {
        this.id = hierarchyGroup.getId();
        this.hierarchyGroupCode = hierarchyGroup.getHierarchyGroupCode();
        this.hierarchyGroupName = hierarchyGroup.getHierarchyGroupName();
        this.isActive = hierarchyGroup.getIsActive();
        this.childGroups = hierarchyGroup.getChildGroup().stream()
                .map(HierarchyGroupResponseTreeDTO::new)
                .collect(Collectors.toList());
    }
}
