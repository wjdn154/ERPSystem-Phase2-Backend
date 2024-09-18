package com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test.*;

import java.util.List;
import java.util.Optional;

public interface HierarchyGroupService {

    void deleteHierarchyGroup(Long id);

    List<HierarchyGroupResponseTreeDTO> getHierarchyGroupTree();

    List<HierarchyGroupWarehouseResponseDTO> getWarehousesByHierarchyGroup(Long groupId);

    void saveHierarchyGroupTest(HierarchyGroupCreateRequestListDTO requestListDTO);

    Optional<HierarchyGroupUpdatedResponseDTO> updateHierarchyGroupTest(Long id, HierarchyGroupUpdatedRequestDTO dto);
}
