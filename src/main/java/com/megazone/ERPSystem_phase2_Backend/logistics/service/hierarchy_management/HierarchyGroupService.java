package com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.CreateHierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.UpdateHierarchyGroupDTO;

import java.util.List;
import java.util.Optional;

public interface HierarchyGroupService {
    List<HierarchyGroupResponseDTO> findAllHierarchyGroup();

    Optional<CreateHierarchyGroupDTO> saveHierarchyGroup(CreateHierarchyGroupDTO dto);

    Optional<HierarchyGroupResponseDTO> updateHierarchyGroup(Long id, UpdateHierarchyGroupDTO dto);

    void deleteHierarchyGroup(Long id);
}
