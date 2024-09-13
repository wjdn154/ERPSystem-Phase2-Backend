package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data.process_routing;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.PrcessRouting.ProcessRoutingRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.process_routing.ProcessRouting.ProcessRoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/production/processRouting")
@RequiredArgsConstructor
public class ProcessRoutingController {

    private final ProcessRoutingService processRoutingService;
    private final ProcessRoutingRepository processRoutingRepository;

    // 1. processRouting 전체 조회
    @GetMapping
    public ResponseEntity<List<ProcessRouting>> getAllProcessRoutings() {
        List<ProcessRouting> processRoutings = processRoutingRepository.findAll();
        return ResponseEntity.ok(processRoutings);
    }

    // 2. processRouting 개별상세조회
    @GetMapping("/{id}")
    public ResponseEntity<ProcessRouting> getProcessRoutingById(@PathVariable("id") Long id) {
        return processRoutingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. processRouting 생성(등록)
    @PostMapping
    public ResponseEntity<ProcessRoutingDTO> createProcessRouting(@RequestBody ProcessRoutingDTO processRoutingDTO) {
        ProcessRoutingDTO createdRouting = processRoutingService.createProcessRoutingWithSteps(processRoutingDTO);
        return ResponseEntity.ok(createdRouting);
    }

    // 3.1 생성 시 등록창 내에서 생산공정 검색 (RoutingStep 등록 위함)
    @GetMapping("/searchProcessDetails")
    public ResponseEntity<List<ProcessDetailsDTO>> searchProcessDetails(@RequestParam String keyword) {
        List<ProcessDetailsDTO> processDetailsList = processRoutingService.searchProcessDetails(keyword);
        return ResponseEntity.ok(processDetailsList);
    }

    // 3.2 생성 시 등록창 내에서 품목Product 검색
    @GetMapping("/searchProducts")
    public ResponseEntity<List<ProductDetailDto>> searchProducts(@RequestParam String keyword) {
        List<ProductDetailDto> productList = processRoutingService.searchProducts(keyword);
        return ResponseEntity.ok(productList);
    }

    // 3.3 선택 시 각 항목을 미리보기로 상세조회
    @GetMapping("/previewProcessDetails/{id}")
    public ResponseEntity<ProcessDetailsDTO> previewProcessDetails(@PathVariable Long id) {
        ProcessDetailsDTO processDetails = processRoutingService.getProcessDetailsById(id);
        return ResponseEntity.ok(processDetails);
    }

    @GetMapping("/previewProduct/{id}")
    public ResponseEntity<ProductDetailDto> previewProduct(@PathVariable Long id) {
        ProductDetailDto product = processRoutingService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // 4. 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProcessRoutingDTO> updateProcessRouting(
            @PathVariable Long id,
            @RequestBody ProcessRoutingDTO processRoutingDTO) {
        ProcessRoutingDTO updatedRouting = processRoutingService.updateProcessRouting(id, processRoutingDTO);
        return ResponseEntity.ok(updatedRouting);
    }


    // 5. 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessRouting(@PathVariable Long id) {
        processRoutingService.deleteProcessRouting(id);
        return ResponseEntity.noContent().build();
    }
}

