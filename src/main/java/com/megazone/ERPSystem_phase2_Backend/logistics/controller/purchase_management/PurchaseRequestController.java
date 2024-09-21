package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
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
     * 발주요청목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<Object> getAllPurchaseRequests() {
        ResponseEntity<Object> response = purchaseRequestService.findAllPurchaseRequests();

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/{id}")
    public ResponseEntity<PurchaseRequestResponseDto> getPurchaseRequestById(@PathVariable("id") Long id) {

        return null;
    }
}