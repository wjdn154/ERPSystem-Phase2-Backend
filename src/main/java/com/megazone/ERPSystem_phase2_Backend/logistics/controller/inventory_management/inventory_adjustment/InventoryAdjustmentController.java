package com.megazone.ERPSystem_phase2_Backend.logistics.controller.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.AdjustmentRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.TransferRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_adjustment.InventoryAdjustmentService;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_transfer.InventoryTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/inventory/adjustment")
public class InventoryAdjustmentController {

    private final InventoryAdjustmentService inventoryAdjustmentService;
    private final InventoryTransferService inventoryTransferService;

    /**
     * 재고 조정/위치 이동
     * @param adjustmentRequestDTO
     * @return ResponseEntity String (성공 메시지)
     */
    @PostMapping("/adjust")
    public ResponseEntity<String> adjustInventory(@RequestBody AdjustmentRequestDTO adjustmentRequestDTO) {
        inventoryAdjustmentService.adjustInventory(adjustmentRequestDTO);
        return ResponseEntity.ok("재고 조정/위치 이동이 성공적으로 완료되었습니다.");
    }

    /**
     * 재고 이동
     * @param transferRequestDTO
     * @return ResponseEntity String (성공 메시지)
     */
    @PostMapping("/transfer")
    public ResponseEntity<String> transferInventory(@RequestBody TransferRequestDTO transferRequestDTO) {
        inventoryTransferService.transferInventory(transferRequestDTO);
        return ResponseEntity.ok("수량 이동이 성공적으로 완료되었습니다.");
    }


}
