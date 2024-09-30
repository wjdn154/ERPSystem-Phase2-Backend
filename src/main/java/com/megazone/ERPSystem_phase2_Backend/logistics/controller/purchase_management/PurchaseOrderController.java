package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase_order.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    /**
     * 발주서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getPurchaseOrders() {
        List<PurchaseOrderResponseDto> response = purchaseOrderService.findAllPurchaseOrders();

        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("발주가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }
}
