package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionRequest.ProductionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/productionRequest")
@RequiredArgsConstructor
public class ProductionRequestController {

    private final ProductionRequestService productionRequestService;

    // 1. 생산 의뢰 생성 (ProgressType = CREATED)
    @PostMapping("/save")
    public ResponseEntity<ProductionRequestDetailDTO> createProductionRequest(
            @RequestBody ProductionRequestDetailDTO dto) {
        ProductionRequestDetailDTO savedRequest = productionRequestService.saveManualProductionRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    // 2. 생산 의뢰 확정 및 MPS 생성 (ProgressType = NOT_STARTED)
    @PostMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmProductionRequest(
            @PathVariable("id") Long id, @RequestParam("confirmedQuantity") Long confirmedQuantity) {
        productionRequestService.confirmProductionRequest(id, confirmedQuantity);
        return ResponseEntity.ok().build();
    }

    /**
     * 생산 요청 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<ProductionRequestDetailDTO> getProductionRequestById(@PathVariable("id") Long id) {
        ProductionRequestDetailDTO productionRequestDTO = productionRequestService.getProductionRequestById(id);
        return ResponseEntity.ok(productionRequestDTO);
    }

    /**
     * 모든 생산 요청 조회
     */
    @PostMapping
    public ResponseEntity<List<ProductionRequestListDTO>> getAllProductionRequests() {
        List<ProductionRequestListDTO> productionRequestDTOS = productionRequestService.getAllProductionRequests();
        return ResponseEntity.ok(productionRequestDTOS);
    }

    /**
     * 생산 요청 수정
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<ProductionRequestDetailDTO> updateProductionRequest(@PathVariable("id") Long id, @RequestBody ProductionRequestDetailDTO dto) {
        ProductionRequestDetailDTO updatedRequest = productionRequestService.updateProductionRequest(id, dto);
        return ResponseEntity.ok(updatedRequest);
    }

    /**
     * 생산 요청 삭제
     */
    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteProductionRequest(@PathVariable("id") Long id) {
        productionRequestService.deleteProductionRequest(id);
        return ResponseEntity.noContent().build();
    }
}
