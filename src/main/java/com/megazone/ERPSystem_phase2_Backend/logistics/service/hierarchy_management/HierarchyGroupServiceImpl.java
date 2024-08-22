package com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HierarchyGroupServiceImpl implements HierarchyGroupService {

    private final HierarchyGroupRepository hierarchyGroupRepository;

    @Override
    public List<HierarchyGroupResponseDTO> findAllHierarchyGroup() {
        return hierarchyGroupRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private HierarchyGroupResponseDTO mapToDTO(HierarchyGroup hierarchyGroup) {
        Long parentGroupId = hierarchyGroup.getParentGroup() != null ? hierarchyGroup.getParentGroup().getId() : null;
        String parentGroupName = hierarchyGroup.getParentGroup() != null ? hierarchyGroup.getParentGroup().getHierarchyGroupName() : null;

        List<HierarchyGroupResponseDTO> childGroups = hierarchyGroup.getChildGroup().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new HierarchyGroupResponseDTO(
                hierarchyGroup.getId(),
                hierarchyGroup.getHierarchyGroupCode(),
                hierarchyGroup.getHierarchyGroupName(),
                hierarchyGroup.getIsActive(),
                parentGroupId,
                parentGroupName,
                childGroups
        );
    }
}
