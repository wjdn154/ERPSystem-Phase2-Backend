package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.receiving_order.ReceivingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getReceivingOrders(@RequestBody(required = false) SearchDTO dto) {
        System.out.println("dto = " + dto);

        List<ReceivingOrderResponseDto> response = receivingOrderService.findAllReceivingOrders(dto);

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
    public ResponseEntity<ReceivingOrderResponseDetailDto> getReceivingOrderDetails(@PathVariable("id") Long id) {

        return receivingOrderService.findReceivingOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 입고지시서 등록
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createReceivingOrder(@RequestBody ReceivingOrderCreateDto createDto) {
        ReceivingOrderResponseDetailDto savedOrder = receivingOrderService.createReceivingOrder(createDto);
        return savedOrder != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedOrder) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("발주서 생성에 실패했습니다.");
    }

    /**
     * 입고지시서 수정
     * @param id
     * @param updateDto
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ReceivingOrderResponseDetailDto> updateReceivingOrder(@PathVariable("id") Long id, @RequestBody ReceivingOrderCreateDto updateDto) {
        ReceivingOrderResponseDetailDto updatedOrder = receivingOrderService.updatePurchaseOrder(id, updateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * 입고지시서 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReceivingOrder(@PathVariable("id") Long id) {
        String result = receivingOrderService.deleteReceivingOrder(id);
        return ResponseEntity.ok(result);
    }
}
