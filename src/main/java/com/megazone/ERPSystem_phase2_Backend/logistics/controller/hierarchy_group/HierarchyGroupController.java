package com.megazone.ERPSystem_phase2_Backend.logistics.controller.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.CreateHierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.UpdateHierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management.HierarchyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics")
public class HierarchyGroupController {

    private final HierarchyGroupService hierarchyGroupService;
    private final HierarchyGroupRepository hierarchyGroupRepository;

    @PostMapping("/hierarchyGroup")
    public ResponseEntity<List<HierarchyGroupResponseDTO>> getAllHierarchyGroups() {
        List<HierarchyGroupResponseDTO> response = hierarchyGroupService.findAllHierarchyGroup();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/hierarchyGroup/saveHierarchyGroup")
    public ResponseEntity<CreateHierarchyGroupDTO> saveHierarchyGroup(@RequestBody CreateHierarchyGroupDTO createHierarchyGroupDTO) {
        Optional<CreateHierarchyGroupDTO> savedHierarchyGroup = hierarchyGroupService.saveHierarchyGroup(createHierarchyGroupDTO);
        return savedHierarchyGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/hierarchyGroup/updateHierarchyGroup/{id}")
    public ResponseEntity<HierarchyGroupResponseDTO> updateHierarchyGroup(@PathVariable("id") Long id, @RequestBody UpdateHierarchyGroupDTO dto) {
        Optional<HierarchyGroupResponseDTO> updateHierarchyGroup = hierarchyGroupService.updateHierarchyGroup(id, dto);

        return updateHierarchyGroup.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/hierarchyGroup/deleteHierarchyGroup/{id}")
    public ResponseEntity<Void> deleteHierarchyGroup(@PathVariable("id") Long id) {
        hierarchyGroupService.deleteHierarchyGroup(id);
        return ResponseEntity.noContent().build();
    }
}
