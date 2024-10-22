package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase_order.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

//    @PostMapping("/")
//    public ResponseEntity<?> getPurchaseOrders() {
//        List<PurchaseOrderResponseDto> response = purchaseOrderService.findAllPurchaseOrders();
//
//        if(response.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("발주가 한 건도 존재하지 않습니다.");
//        }
//
//        return ResponseEntity.ok(response);
//    }
    /**
     * 발주서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getPurchaseOrders(@RequestBody(required = false) SearchDTO dto) {
        System.out.println("dto = " + dto);
        List<PurchaseOrderResponseDto> response = purchaseOrderService.findAllPurchaseOrders(dto);

        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("발주가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 발주서 상세 조회
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDetailDto> getPurchaseOrderDetails(@PathVariable("id") Long id) {

        return purchaseOrderService.findPurchaseOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 발주서 등록
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrderCreateDto createDto) {
        PurchaseOrderResponseDetailDto savedOrder = purchaseOrderService.createPurchaseOrder(createDto);
        return savedOrder != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedOrder) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("발주서 생성에 실패했습니다.");
    }

    /**
     * 발주서 수정
     * @param id
     * @param updateDto
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PurchaseOrderResponseDetailDto> updatePurchaseRequest(@PathVariable("id") Long id, @RequestBody PurchaseOrderCreateDto updateDto) {
        PurchaseOrderResponseDetailDto updatedOrder = purchaseOrderService.updatePurchaseOrder(id, updateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * 발주서 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePurchaseOrder(@PathVariable("id") Long id) {
        String result = purchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok(result);
    }
}
