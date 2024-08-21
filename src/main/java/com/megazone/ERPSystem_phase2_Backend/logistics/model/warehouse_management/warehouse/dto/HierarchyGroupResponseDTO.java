package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto;

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
    private String parentGroupName; // 상위 그룹명 같이 보여줄 수 있음
    private List<HierarchyGroupResponseDTO> childGroups;
}

