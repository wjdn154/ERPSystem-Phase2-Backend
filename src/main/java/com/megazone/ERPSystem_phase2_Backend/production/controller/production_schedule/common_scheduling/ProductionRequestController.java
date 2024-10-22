package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder.ProductionOrderService;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionRequest.ProductionRequestService;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mps.MpsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/production/productionRequest")
@RequiredArgsConstructor
public class ProductionRequestController {

    private final ProductionRequestService productionRequestService;

    // 1. 생산 의뢰 생성 (ProgressType = CREATED)
    @PostMapping("/save")
    public ResponseEntity<ProductionRequestDTO> createProductionRequest(
            @RequestBody ProductionRequestDTO requestDTO) {
        ProductionRequestDTO savedRequest = productionRequestService.saveManualProductionRequest(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    // 2. 생산 의뢰 확정 및 MPS 생성 (ProgressType = NOT_STARTED)
    @PostMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmProductionRequest(
            @PathVariable Long id, @RequestParam BigDecimal confirmedQuantity) {
        productionRequestService.confirmProductionRequest(id, confirmedQuantity);
        return ResponseEntity.ok().build();
    }

    /**
     * 생산 요청 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<ProductionRequestDTO> getProductionRequestById(@PathVariable Long id) {
        ProductionRequestDTO productionRequestDTO = productionRequestService.getProductionRequestById(id);
        return ResponseEntity.ok(productionRequestDTO);
    }

    /**
     * 모든 생산 요청 조회
     */
    @PostMapping
    public ResponseEntity<List<ProductionRequestDTO>> getAllProductionRequests() {
        List<ProductionRequestDTO> productionRequestDTOS = productionRequestService.getAllProductionRequests();
        return ResponseEntity.ok(productionRequestDTOS);
    }

    /**
     * 생산 요청 수정
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<ProductionRequestDTO> updateProductionRequest(@PathVariable Long id, @RequestBody ProductionRequestDTO productionRequestDTO) {
        ProductionRequestDTO updatedRequest = productionRequestService.updateProductionRequest(id, productionRequestDTO);
        return ResponseEntity.ok(updatedRequest);
    }

    /**
     * 생산 요청 삭제
     */
    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteProductionRequest(@PathVariable Long id) {
        productionRequestService.deleteProductionRequest(id);
        return ResponseEntity.noContent().build();
    }
}
