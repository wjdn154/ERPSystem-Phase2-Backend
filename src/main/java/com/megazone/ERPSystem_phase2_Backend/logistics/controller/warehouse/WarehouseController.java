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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseRepository warehouseRepository;
    private final HierarchyGroupService hierarchyGroupService;
    private final HierarchyGroupRepository hierarchyGroupRepository;

    /**
     * 모든 창고리스트들을 가져옴.
     * @return 모든 창고 정보를 담은 WarehouseResponseDTO 객체를 반환함
     */
    @PostMapping("/warehouse/showall/{companyId}")
    public ResponseEntity<List<WarehouseResponseDTO>> getAllWarehouse(@PathVariable("companyId") Long companyId) {
        // 리포지토리에서 모든 창고리스트들을 가져옴
        List<WarehouseResponseDTO> response = warehouseService.findAllWarehouses(companyId);
        return ResponseEntity.ok(response);
    }

    /**
     * 창고 상세 정보를 가져옴.
     * @param id
     * @return 창고 상세 정보를 담은 WarehouseDetailDTO 객체를 반환함.
     */
    @PostMapping("/warehouse/show/{id}")
    public ResponseEntity<WarehouseDetailDTO> getWarehouseDetail(@PathVariable("id") Long id) {
        WarehouseDetailDTO warehouseDetail = warehouseService.getWarehouseDetail(id);

        return ResponseEntity.ok(warehouseDetail);
    }

    /**
     *
     * @param warehouseDetailDTO
     * @return
     */
    @PostMapping("/warehouse/saveWarehouse")
    public ResponseEntity<WarehouseDetailDTO> saveWarehouse(@RequestBody WarehouseDetailDTO warehouseDetailDTO) {
        Optional<WarehouseDetailDTO> savedWarehouse = warehouseService.saveWarehouse(warehouseDetailDTO);
        return savedWarehouse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/warehouse/updateWarehouse/{id}")
    public ResponseEntity<UpdateWarehouseDTO> updateWarehouse(@PathVariable("id") Long id, @RequestBody UpdateWarehouseDTO dto) {
        Optional<UpdateWarehouseDTO> updateWarehouse = warehouseService.updateWarehouse(id, dto);
        return updateWarehouse
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/warehouse/deleteWarehouse/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable("id") Long id) {
        try {
            String result = warehouseService.deleteWarehouse(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("창고 삭제 중 오류가 발생했습니다.");
        }
    }
}
