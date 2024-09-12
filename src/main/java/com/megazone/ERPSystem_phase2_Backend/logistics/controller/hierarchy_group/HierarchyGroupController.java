package com.megazone.ERPSystem_phase2_Backend.logistics.controller.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.HierarchyGroupResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.UpdateHierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management.HierarchyGroupService;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = user.getCompany().getId();
        List<HierarchyGroupResponseTreeDTO> tree = hierarchyGroupService.getHierarchyGroupTree(companyId);
        return ResponseEntity.ok(tree);
    }

    @PostMapping("/{groupId}/warehouses")
    public ResponseEntity<List<HierarchyGroupWarehouseResponseDTO>> getHierarchyGroupWarehouses(@PathVariable Long groupId) {
        List<HierarchyGroupWarehouseResponseDTO> warehouses = hierarchyGroupService.getWarehousesByHierarchyGroup(groupId);
        return ResponseEntity.ok(warehouses);
    }

    @PostMapping("/saveHierarchyGroup")
    public ResponseEntity<Void> saveHierarchyGroupTest(@RequestBody HierarchyGroupCreateRequestListDTO requestListDTO) {

        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = user.getCompany().getId();

        hierarchyGroupService.saveHierarchyGroupTest(requestListDTO, companyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/test/update/{id}")
    public ResponseEntity<HierarchyGroupUpdatedResponseDTO> updateHierarchyGroupTest(@PathVariable("id") Long id, @RequestBody HierarchyGroupUpdatedRequestDTO dto) {

        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = user.getCompany().getId();

        Optional<HierarchyGroupUpdatedResponseDTO> updatedGroup = hierarchyGroupService.updateHierarchyGroupTest(id, dto, companyId);
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
