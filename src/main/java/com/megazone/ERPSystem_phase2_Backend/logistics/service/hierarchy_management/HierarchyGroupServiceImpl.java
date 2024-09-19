package com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public void saveHierarchyGroupTest(HierarchyGroupCreateRequestListDTO requestListDTO) {

        HierarchyGroup parentGroup = hierarchyGroupRepository.findById(requestListDTO.getParentGroupId())
                .orElseThrow(() -> new IllegalArgumentException("상위 그룹을 찾을 수 없습니다."));

        for (HierarchyGroupCreateRequestDTO groupRequest : requestListDTO.getHierarchyGroups()) {
            HierarchyGroup newGroup = new HierarchyGroup();
            newGroup.setHierarchyGroupCode(groupRequest.getHierarchyGroupCode());
            newGroup.setHierarchyGroupName(groupRequest.getHierarchyGroupName());
            newGroup.setIsActive(groupRequest.getIsActive());
            newGroup.setParentGroup(parentGroup);

            hierarchyGroupRepository.save(newGroup);
        }
    }

    @Override
    public Optional<HierarchyGroupUpdatedResponseDTO> updateHierarchyGroupTest(Long id, HierarchyGroupUpdatedRequestDTO dto) {

        HierarchyGroup existingGroup = hierarchyGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 계층 그룹을 찾을 수 없습니다."));

        HierarchyGroup parentGroup = dto.getParentGroupId() != null ? hierarchyGroupRepository.findById(dto.getParentGroupId())
                .orElseThrow(() -> new RuntimeException("부모 그룹을 찾을 수 없습니다.")) : null;

        existingGroup.setHierarchyGroupCode(dto.getHierarchyGroupCode());
        existingGroup.setHierarchyGroupName(dto.getHierarchyGroupName());
        existingGroup.setIsActive(dto.getIsActive());
        existingGroup.setParentGroup(parentGroup);

        HierarchyGroup updatedGroup = hierarchyGroupRepository.save(existingGroup);

        HierarchyGroupUpdatedResponseDTO responseDTO = new HierarchyGroupUpdatedResponseDTO(updatedGroup);

        return Optional.of(responseDTO);
    }


    @Override
    public void deleteHierarchyGroup(Long id) {
        HierarchyGroup group = hierarchyGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("계층 그룹을 찾을 수 없습니다: " + id));

        hierarchyGroupRepository.delete(group);
    }

    @Override
    public List<HierarchyGroupResponseTreeDTO> getHierarchyGroupTree() {
        List<HierarchyGroup> groups = hierarchyGroupRepository.findByParentGroupIsNull();
        return groups.stream()
                .map(HierarchyGroupResponseTreeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<HierarchyGroupWarehouseResponseDTO> getWarehousesByHierarchyGroup(Long groupId) {
        List<Warehouse> warehouses = hierarchyGroupRepository.findWarehousesByHierarchyGroupId(groupId);
        return warehouses.stream()
                .map(HierarchyGroupWarehouseResponseDTO::new)
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
