package com.megazone.ERPSystem_phase2_Backend.logistics.controller.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseRegistrationService;
    private final WarehouseRepository warehouseRepository;

    /**
     * 모든 창고리스트들을 가져옴.
     * @return 모든 창고 정보를 담은 WarehouseDTO 객체를 반환함
     */
    @PostMapping("/api/logistics/warehouse")
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouse() {
        // 리포지토리에서 모든 창고리스트들을 가져옴
        List<WarehouseDTO> response = warehouseRegistrationService.findAllWarehouses();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/logistics/warehouse/{id}")
    public ResponseEntity<WarehouseDetailDTO> getWarehouseDetail(@PathVariable Long id) {
        WarehouseDetailDTO warehouseDetail = warehouseRegistrationService.getWarehouseDetail(id);

        return ResponseEntity.ok(warehouseDetail);
    }




}
