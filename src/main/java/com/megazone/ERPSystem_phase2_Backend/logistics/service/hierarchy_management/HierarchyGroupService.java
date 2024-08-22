package com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;

import java.util.List;

public interface HierarchyGroupService {
    List<HierarchyGroupResponseDTO> findAllHierarchyGroup();
}
