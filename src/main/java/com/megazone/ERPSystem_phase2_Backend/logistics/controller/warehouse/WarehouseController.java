package com.megazone.ERPSystem_phase2_Backend.logistics.controller.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.hierarchy_management.HierarchyGroupService;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseRepository warehouseRepository;
    private final HierarchyGroupService hierarchyGroupService;
    private final HierarchyGroupRepository hierarchyGroupRepository;

    /**
     * 모든 창고리스트들을 가져옴.
     * @return 모든 창고 정보를 담은 WarehouseDTO 객체를 반환함
     */
    @PostMapping("/api/logistics/warehouse")
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouse() {
        // 리포지토리에서 모든 창고리스트들을 가져옴
        List<WarehouseDTO> response = warehouseService.findAllWarehouses();
        return ResponseEntity.ok(response);
    }

    /**
     * 창고 상세 정보를 가져옴.
     * @param id
     * @return 창고 상세 정보를 담은 WarehouseDetailDTO 객체를 반환함.
     */
    @PostMapping("/api/logistics/warehouse/{id}")
    public ResponseEntity<WarehouseDetailDTO> getWarehouseDetail(@PathVariable Long id) {
        WarehouseDetailDTO warehouseDetail = warehouseService.getWarehouseDetail(id);

        return ResponseEntity.ok(warehouseDetail);
    }

    @PostMapping("/api/logistics/warehouse/saveWarehouse")
    public ResponseEntity<WarehouseDetailDTO> saveWarehouse(@RequestBody WarehouseDetailDTO warehouseDetailDTO) {
        Optional<WarehouseDetailDTO> savedWarehouse = warehouseService.saveWarehouse(warehouseDetailDTO);
        return savedWarehouse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/api/logistics/hierarchyGroup")
    public ResponseEntity<List<HierarchyGroupResponseDTO>> getAllHierarchyGroups() {
        List<HierarchyGroupResponseDTO> response = hierarchyGroupService.findAllHierarchyGroup();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/logistics/hierarchyGroup/saveHierarchyGroup")
    public ResponseEntity<CreateHierarchyGroupDTO> saveHierarchyGroup(@RequestBody CreateHierarchyGroupDTO createHierarchyGroupDTO) {
        Optional<CreateHierarchyGroupDTO> savedHierarchyGroup = hierarchyGroupService.saveHierarchyGroup(createHierarchyGroupDTO);
        return savedHierarchyGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/api/logistics/hierarchyGroup/updateHierarchyGroup/{id}")
    public ResponseEntity<HierarchyGroupResponseDTO> updateHierarchyGroup(@PathVariable Long id, @RequestBody UpdateHierarchyGroupDTO dto) {
        Optional<HierarchyGroupResponseDTO> updateHierarchyGroup = hierarchyGroupService.updateHierarchyGroup(id, dto);

        return updateHierarchyGroup.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/api/logistics/hierarchyGroup/deleteHierarchyGroup/{id}")
    public ResponseEntity deleteHierarchyGroup(@PathVariable Long id) {
        hierarchyGroupService.deleteHierarchyGroup(id);
        return ResponseEntity.noContent().build();
    }

}
