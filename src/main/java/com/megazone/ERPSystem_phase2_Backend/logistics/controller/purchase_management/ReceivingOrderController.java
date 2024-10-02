package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.ReceivingOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.receiving_order.ReceivingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/receiving-orders")
public class ReceivingOrderController {

    private final ReceivingOrderService receivingOrderService;


    /**
     * 입고지시서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getReceivingOrders() {

        List<ReceivingOrderResponseDto> response = receivingOrderService.findAllReceivingOrders();

        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("입고지시서가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 입고지시서 상세 조회
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public ResponseEntity<ReceivingOrderResponseDto> getReceivingOrderDetails(@PathVariable("id") Long id) {

        return receivingOrderService.findReceivingOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
