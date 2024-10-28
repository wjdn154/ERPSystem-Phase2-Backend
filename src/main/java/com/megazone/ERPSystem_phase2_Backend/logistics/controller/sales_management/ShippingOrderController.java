package com.megazone.ERPSystem_phase2_Backend.logistics.controller.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.shipping_order.ShippingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/shipping-orders")
public class ShippingOrderController {

    private final ShippingOrderService shippingOrderService;


    /**
     * 출하지시서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getShippingOrders(@RequestBody(required = false) SearchDTO dto) {
        System.out.println("dto = " + dto);

        List<ShippingOrderResponseDto> response = shippingOrderService.findAllShippingOrders(dto);

        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("출하지시서가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 출하지시서 상세 조회
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public ResponseEntity<ShippingOrderResponseDetailDto> getShippingOrderDetails(@PathVariable("id") Long id) {

        return shippingOrderService.findShippingOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 출하지시서 등록
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createShippingOrder(@RequestBody ShippingOrderCreateDto createDto) {
        ShippingOrderResponseDetailDto savedOrder = shippingOrderService.createShippingOrder(createDto);
        return savedOrder != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedOrder) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("발주서 생성에 실패했습니다.");
    }

    /**
     * 출하지시서 수정
     * @param id
     * @param updateDto
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ShippingOrderResponseDetailDto> updateShippingOrder(@PathVariable("id") Long id, @RequestBody ShippingOrderCreateDto updateDto) {
        ShippingOrderResponseDetailDto updatedOrder = shippingOrderService.updateShippingOrder(id, updateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * 출하지시서 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShippingOrder(@PathVariable("id") Long id) {
        String result = shippingOrderService.deleteShippingOrder(id);
        return ResponseEntity.ok(result);
    }
}
