package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/purchase-requests")
public class PurchaseController {

    private final PurchaseService purchaseService;

    /**
     * 구매서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getPurchase() {
        List<PurchaseResponseDto> response = purchaseService.findAllPurchases();

        if(response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("구매서가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    @Post
}
