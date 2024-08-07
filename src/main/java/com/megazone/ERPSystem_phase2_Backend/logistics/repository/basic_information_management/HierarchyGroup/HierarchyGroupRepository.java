package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.HierarchyGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.HierarchyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HierarchyGroupRepository extends JpaRepository<HierarchyGroup, Long>, HierarchyGroupRepositoryCustom {
    Optional<HierarchyGroup> findByName(String name);

    Optional<HierarchyGroup> findByCode(String code);
}
