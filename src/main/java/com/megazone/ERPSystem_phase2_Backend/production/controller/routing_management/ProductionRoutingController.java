package com.megazone.ERPSystem_phase2_Backend.production.controller.routing_management;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting.ProductionRoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/production/productionRouting")
@RequiredArgsConstructor
public class ProductionRoutingController {

    private final ProductionRoutingService productionRoutingService;
    private final ProductionRoutingRepository productionRoutingRepository;

    // 1. productionRouting 전체 조회
    @GetMapping
    public ResponseEntity<List<ProductionRouting>> getAllProductionRoutings() {
        List<ProductionRouting> productionRoutings = productionRoutingRepository.findAll();
        return ResponseEntity.ok(productionRoutings);
    }

    // 2. productionRouting 개별상세조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductionRouting> getProductionRoutingById(@PathVariable("id") Long id) {
        return productionRoutingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. productionRouting 생성(등록)
    @PostMapping
    public ResponseEntity<ProductionRoutingDTO> createProductionRouting(@RequestBody ProductionRoutingDTO productionRoutingDTO) {
        ProductionRoutingDTO createdRouting = productionRoutingService.createProductionRoutingWithSteps(productionRoutingDTO);
        return ResponseEntity.ok(createdRouting);
    }

    // 3.1 생성 시 등록창 내에서 생산공정 검색 (RoutingStep 등록 위함)
    @GetMapping("/searchProcessDetails")
    public ResponseEntity<List<ProcessDetailsDTO>> searchProcessDetails(@RequestParam String keyword) {
        List<ProcessDetailsDTO> processDetailsList = productionRoutingService.searchProcessDetails(keyword);
        return ResponseEntity.ok(processDetailsList);
    }

    // 3.2 생성 시 등록창 내에서 품목Product 검색
    @GetMapping("/searchProducts")
    public ResponseEntity<List<ProductDetailDto>> searchProducts(@RequestParam String keyword) {
        List<ProductDetailDto> productList = productionRoutingService.searchProducts(keyword);
        return ResponseEntity.ok(productList);
    }

    // 3.3 선택 시 각 항목을 미리보기로 상세조회
    @GetMapping("/previewProcessDetails/{id}")
    public ResponseEntity<ProcessDetailsDTO> previewProcessDetails(@PathVariable Long id) {
        ProcessDetailsDTO processDetails = productionRoutingService.getProcessDetailsById(id);
        return ResponseEntity.ok(processDetails);
    }

    @GetMapping("/previewProduct/{id}")
    public ResponseEntity<ProductDetailDto> previewProduct(@PathVariable Long id) {
        ProductDetailDto product = productionRoutingService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // 4. 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductionRoutingDTO> updateProductionRouting(
            @PathVariable Long id,
            @RequestBody ProductionRoutingDTO productionRoutingDTO) {
        ProductionRoutingDTO updatedRouting = productionRoutingService.updateProductionRouting(id, productionRoutingDTO);
        return ResponseEntity.ok(updatedRouting);
    }


    // 5. 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductionRouting(@PathVariable Long id) {
        productionRoutingService.deleteProductionRouting(id);
        return ResponseEntity.noContent().build();
    }
}

