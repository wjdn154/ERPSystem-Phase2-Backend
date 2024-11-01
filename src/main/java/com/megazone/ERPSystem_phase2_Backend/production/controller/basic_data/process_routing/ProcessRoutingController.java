package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data.process_routing;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDetail2DTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.PrcessRouting.ProcessRoutingRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.process_routing.ProcessRouting.ProcessRoutingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/production/processRouting")
@RequiredArgsConstructor
public class ProcessRoutingController {

    private final ProcessRoutingService processRoutingService;
    private final ProcessRoutingRepository processRoutingRepository;
    private final ProductRepository productRepository;

    // 1. processRouting 전체 조회
    @PostMapping
    public ResponseEntity<List<ProcessRoutingDTO>> getAllProcessRoutings() {

        ModelMapper modelMapper = new ModelMapper();
        List<ProcessRoutingDTO> processRoutings = processRoutingRepository.findAll().stream()
                .map(bank -> modelMapper.map(bank, ProcessRoutingDTO.class))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(processRoutings);
    }

    // 2. processRouting 개별상세조회
    @PostMapping("/{id}")
    public ResponseEntity<ProcessRoutingDetailDTO> getProcessRoutingById(@PathVariable("id") Long id) {

        ProcessRoutingDetailDTO processRoutings = processRoutingService.getProcessRoutingById(id);

        return ResponseEntity.status(HttpStatus.OK).body(processRoutings);
    }

    // 3. processRouting 생성(등록)
    @PostMapping("/new")
    public ResponseEntity<ProcessRoutingDTO> createProcessRouting(@RequestBody ProcessRoutingDTO processRoutingDTO) {
        ProcessRoutingDTO createdRouting = processRoutingService.createProcessRoutingWithSteps(processRoutingDTO);
        return ResponseEntity.ok(createdRouting);
    }

    // 3.1 생성 시 등록창 내에서 생산공정 검색 (RoutingStep 등록 위함)
    @PostMapping("/searchProcessDetails")
    public ResponseEntity<List<ProcessDetailsDTO>> searchProcessDetails() {
        List<ProcessDetailsDTO> processDetailsList = processRoutingService.searchProcessDetails();
        return ResponseEntity.ok(processDetailsList);
    }

    // 3.2 생성 시 등록창 내에서 품목Product 검색
    @PostMapping("/searchProducts")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam(value = "keyword") String keyword) {
        List<ProductDto> productList = processRoutingService.searchProducts(keyword);
        return ResponseEntity.ok(productList);
    }

    // 3.3 선택 시 각 항목을 미리보기로 상세조회
    @PostMapping("/previewProcessDetails/{id}")
    public ResponseEntity<ProcessDetailsDTO> previewProcessDetails(@PathVariable("id") Long id) {
        ProcessDetailsDTO processDetails = processRoutingService.getProcessDetailsById(id);
        return ResponseEntity.ok(processDetails);
    }

    @PostMapping("/previewProduct/{id}")
    public ResponseEntity<Optional<Product>> previewProduct(@PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        return ResponseEntity.ok(product);
    }

    // 4. 수정
    @PostMapping("update")
    public ResponseEntity<ProcessRoutingDetailDTO> updateProcessRouting(@RequestBody ProcessRoutingDetailDTO processRoutingDetailDTO) {
        ProcessRoutingDetailDTO updatedRouting = processRoutingService.updateProcessRouting(processRoutingDetailDTO);
        return ResponseEntity.ok(updatedRouting);
    }

    // 5. 삭제
    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteProcessRouting(@PathVariable("id") Long id) {
        processRoutingService.deleteProcessRouting(id);
        return ResponseEntity.noContent().build();
    }
}

