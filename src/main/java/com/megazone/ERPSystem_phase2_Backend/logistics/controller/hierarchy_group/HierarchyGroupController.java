package com.megazone.ERPSystem_phase2_Backend.logistics.controller.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management.HierarchyGroupService;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/hierarchyGroup")
public class HierarchyGroupController {

    private final HierarchyGroupService hierarchyGroupService;
    private final WarehouseService warehouseService;
    private final UsersRepository usersRepository;
    private final HierarchyGroupRepository hierarchyGroupRepository;

    @PostMapping("/")
    public ResponseEntity<List<HierarchyGroupResponseTreeDTO>> getHierarchyGroupTree() {
        List<HierarchyGroupResponseTreeDTO> tree = hierarchyGroupService.getHierarchyGroupTree();
        return ResponseEntity.ok(tree);
    }

    @PostMapping("/{groupId}/warehouses")
    public ResponseEntity<List<HierarchyGroupWarehouseResponseDTO>> getHierarchyGroupWarehouses(@PathVariable Long groupId) {
        List<HierarchyGroupWarehouseResponseDTO> warehouses = hierarchyGroupService.getWarehousesByHierarchyGroup(groupId);
        return ResponseEntity.ok(warehouses);
    }

    @PostMapping("/saveHierarchyGroup")
    public ResponseEntity<Void> saveHierarchyGroupTest(@RequestBody HierarchyGroupCreateRequestListDTO requestListDTO) {
        hierarchyGroupService.saveHierarchyGroupTest(requestListDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/test/update/{id}")
    public ResponseEntity<HierarchyGroupUpdatedResponseDTO> updateHierarchyGroupTest(@PathVariable("id") Long id, @RequestBody HierarchyGroupUpdatedRequestDTO dto) {

        Optional<HierarchyGroupUpdatedResponseDTO> updatedGroup = hierarchyGroupService.updateHierarchyGroupTest(id, dto);
        return updatedGroup
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/deleteHierarchyGroup/{id}")
    public ResponseEntity<Void> deleteHierarchyGroup(@PathVariable("id") Long id) {
        hierarchyGroupService.deleteHierarchyGroup(id);
        return ResponseEntity.noContent().build();
    }
}
