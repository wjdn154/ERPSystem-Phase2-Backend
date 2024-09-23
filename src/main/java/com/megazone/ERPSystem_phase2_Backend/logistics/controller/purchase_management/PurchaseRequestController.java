package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/purchase-requests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    /**
     * 발주요청 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getAllPurchaseRequests() {
        List<PurchaseRequestResponseDto> response = purchaseRequestService.findAllPurchaseRequests();

        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("발주 요청이 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 발주요청 상세 조회
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public ResponseEntity<PurchaseRequestResponseDetailDto> getPurchaseRequestById(@PathVariable("id") Long id) {

        return purchaseRequestService.findPurchaseRequestDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPurchaseRequest(@RequestBody PurchaseRequestCreateDto creationDto) {
        PurchaseRequestResponseDetailDto savedRequest = purchaseRequestService.createPurchaseRequest(creationDto);
        return savedRequest != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedRequest) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("발주 요청 생성에 실패했습니다.");
    }
}