package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyGroupCreateRequestDTO {
    private String hierarchyGroupCode;
    private String hierarchyGroupName;
    private Boolean isActive; // 기본값이 정해져 있을 수 있음
}
