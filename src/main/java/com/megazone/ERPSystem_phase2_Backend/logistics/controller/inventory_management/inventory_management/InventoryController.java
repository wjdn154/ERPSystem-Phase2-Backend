package com.megazone.ERPSystem_phase2_Backend.logistics.controller.inventory_management.inventory_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logistics/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * 모든 재고 조회
     * @return List<InventoryResponseDTO>
     */
    @PostMapping("/")
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventories() {
        List<InventoryResponseDTO> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    /**
     * 특정 위치의 재고 조회
     * @param locationId
     * @return List<InventoryResponseDTO>
     */
    @PostMapping("/location/{locationId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoriesByLocationId(@PathVariable("locationId") Long locationId) {
        List<InventoryResponseDTO> inventories = inventoryService.getInventoriesByLocationId(locationId);
        return ResponseEntity.ok(inventories);
    }

    /**
     * 재고 생성
     * @param requestDTO
     * @return InventoryResponseDTO
     */
    @PostMapping("/create")
    public ResponseEntity<InventoryResponseDTO> createInventory(@RequestBody InventoryRequestDTO requestDTO) {
        InventoryResponseDTO responseDTO = inventoryService.createInventory(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * 창고별 재고 조회
     * @param warehouseId
     *
     */
    @PostMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoriesByWarehouseId(@PathVariable("warehouseId") Long warehouseId) {
        List<InventoryResponseDTO> inventories = inventoryService.getInventoriesByWarehouseId(warehouseId);
        return ResponseEntity.ok(inventories);
    }

    /**
     * 로케이션별 재고 조회
     * @param locationId
     * @return
     */
    @PostMapping("/byLocation/{locationId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoryByLocation(@PathVariable("locationId") Long locationId) {
        List<InventoryResponseDTO> inventoryList = inventoryService.getInventoryByLocation(locationId);
        return ResponseEntity.ok(inventoryList);
    }

    @PostMapping("/nextInventoryNumber")
    public ResponseEntity<Long> getNextInventoryNumber() {
        Long nextInventoryNumber = inventoryService.generateNextInventoryNumber();
        return ResponseEntity.ok(nextInventoryNumber);
    }

}


