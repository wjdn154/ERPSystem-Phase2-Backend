package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.plan_of_production;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.plan_of_production.ProductionRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/productionRequests")
@RequiredArgsConstructor
public class ProductionRequestsController {

    private final ProductionRequestsService productionRequestsService;

    /**
     * 생산 요청 수동 등록
     */
    @PostMapping("/create")
    public ResponseEntity<ProductionRequestsDTO> createProductionRequest(@RequestBody ProductionRequestsDTO productionRequestsDTO) {
        ProductionRequestsDTO savedRequest = productionRequestsService.saveManualProductionRequest(productionRequestsDTO);
        return ResponseEntity.ok(savedRequest);
    }

    /**
     * 생산 요청 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<ProductionRequestsDTO> getProductionRequestById(@PathVariable Long id) {
        ProductionRequestsDTO productionRequestsDTO = productionRequestsService.getProductionRequestById(id);
        return ResponseEntity.ok(productionRequestsDTO);
    }

    /**
     * 모든 생산 요청 조회
     */
    @PostMapping
    public ResponseEntity<List<ProductionRequestsDTO>> getAllProductionRequests() {
        List<ProductionRequestsDTO> productionRequestsDTOs = productionRequestsService.getAllProductionRequests();
        return ResponseEntity.ok(productionRequestsDTOs);
    }

    /**
     * 생산 요청 수정
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<ProductionRequestsDTO> updateProductionRequest(@PathVariable Long id, @RequestBody ProductionRequestsDTO productionRequestsDTO) {
        ProductionRequestsDTO updatedRequest = productionRequestsService.updateProductionRequest(id, productionRequestsDTO);
        return ResponseEntity.ok(updatedRequest);
    }

    /**
     * 생산 요청 삭제
     */
    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteProductionRequest(@PathVariable Long id) {
        productionRequestsService.deleteProductionRequest(id);
        return ResponseEntity.noContent().build();
    }



}
