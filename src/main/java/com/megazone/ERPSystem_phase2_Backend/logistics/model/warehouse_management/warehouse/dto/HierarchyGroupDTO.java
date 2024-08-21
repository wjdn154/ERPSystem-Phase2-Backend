package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.WarehouseHierarchyGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyGroupDTO {
    private Long id;
    private String code;
    private String name;
    private Boolean isActive;
//    private List<HierarchyGroupDTO> childHierarchyGroups;
}
