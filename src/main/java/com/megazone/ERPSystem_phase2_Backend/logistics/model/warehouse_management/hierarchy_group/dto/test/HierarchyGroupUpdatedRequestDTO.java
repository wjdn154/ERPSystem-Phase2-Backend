package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyGroupUpdatedRequestDTO {
    private String name;
    private String hierarchyGroupCode;
    private String hierarchyGroupName;
    private Long parentGroupId;
    private Boolean isActive;

}
