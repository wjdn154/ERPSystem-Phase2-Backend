package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HierarchyGroupRepository extends JpaRepository<HierarchyGroup, Long>, HierarchyGroupRepositoryCustom {
    Optional<HierarchyGroup> findByHierarchyGroupCode(String code);

    List<HierarchyGroup> findByCompanyIdAndParentGroupIsNull(Long companyId);

    HierarchyGroup findByIdAndCompanyId(Long id, Long companyId);
}
