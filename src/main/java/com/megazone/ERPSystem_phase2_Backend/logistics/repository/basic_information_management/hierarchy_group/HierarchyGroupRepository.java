package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HierarchyGroupRepository extends JpaRepository<HierarchyGroup, Long>, HierarchyGroupRepositoryCustom {
    Optional<HierarchyGroupResponseDTO> findByHierarchyGroupCode(String code);

    Optional<HierarchyGroupResponseDTO> findByHierarchyGroupName(String name);
}
