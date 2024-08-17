package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyGroupDTO {
    private Long id;
    private String hierarchyGroupCode;
    private String hierarchyGroupName;
    private String warehouseHierarchyGroup;
}
