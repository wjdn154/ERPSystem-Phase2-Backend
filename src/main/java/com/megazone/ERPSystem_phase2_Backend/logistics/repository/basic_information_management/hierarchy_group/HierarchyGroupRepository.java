package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.HierarchyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HierarchyGroupRepository extends JpaRepository<HierarchyGroup, Long>, HierarchyGroupRepositoryCustom {
}
