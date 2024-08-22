package com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.CreateHierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.UpdateHierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<CreateHierarchyGroupDTO> saveHierarchyGroup(CreateHierarchyGroupDTO dto) {

        HierarchyGroup group = new HierarchyGroup();

        hierarchyGroupRepository.findByHierarchyGroupCode(dto.getHierarchyGroupCode())
                .ifPresent(hierarchyGroup -> {throw new RuntimeException("이미 존재하는 코드입니다: " + dto.getHierarchyGroupCode());
                });

        group.setHierarchyGroupName(dto.getHierarchyGroupName());
        group.setHierarchyGroupCode(dto.getHierarchyGroupCode());
        group.setIsActive(dto.getIsActive());

        if (dto.getParentGroupId() != null) {
            HierarchyGroup parentGroup = hierarchyGroupRepository.findById(dto.getParentGroupId())
                    .orElseThrow(() -> new RuntimeException("상위 그룹을 찾을 수 없습니다: " + dto.getParentGroupId()));
            group.setParentGroup(parentGroup);
        }

        HierarchyGroup savedEntity = hierarchyGroupRepository.save(group);

        CreateHierarchyGroupDTO createHierarchyGroupDTO = new CreateHierarchyGroupDTO();
        createHierarchyGroupDTO.setParentGroupId(savedEntity.getId());
        createHierarchyGroupDTO.setHierarchyGroupCode(savedEntity.getHierarchyGroupCode());
        createHierarchyGroupDTO.setHierarchyGroupName(savedEntity.getHierarchyGroupName());
        createHierarchyGroupDTO.setIsActive(savedEntity.getIsActive());

        return Optional.of(createHierarchyGroupDTO);
    }

    @Override
    public Optional<HierarchyGroupResponseDTO> updateHierarchyGroup(Long id, UpdateHierarchyGroupDTO dto) {
        HierarchyGroup group = hierarchyGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("계층 그룹을 찾을 수 없습니다: " + id));

        group.setHierarchyGroupCode(dto.getHierarchyGroupCode());
        group.setHierarchyGroupName(dto.getHierarchyGroupName());
        group.setIsActive(dto.getIsActive());

        if (dto.getParentGroupId() != null) {
            HierarchyGroup parentGroup = hierarchyGroupRepository.findById(dto.getParentGroupId())
                    .orElseThrow(() -> new RuntimeException("상위 그룹을 찾을 수 없습니다:" + dto.getParentGroupId()));
            group.setParentGroup(parentGroup);
        } else {
            group.setParentGroup(null);
        }

        hierarchyGroupRepository.save(group);

        HierarchyGroupResponseDTO responseDTO = new HierarchyGroupResponseDTO(
                group.getId(),
                group.getHierarchyGroupCode(),
                group.getHierarchyGroupName(),
                group.getIsActive(),
                group.getParentGroup() != null ? group.getParentGroup().getId() : null,
                group.getParentGroup() != null ? group.getParentGroup().getHierarchyGroupName() : null,
                null
        );
        return Optional.of(responseDTO);
    }

    @Override
    public void deleteHierarchyGroup(Long id) {
        HierarchyGroup group = hierarchyGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("계층 그룹을 찾을 수 없습니다: " + id));

        hierarchyGroupRepository.delete(group);
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
