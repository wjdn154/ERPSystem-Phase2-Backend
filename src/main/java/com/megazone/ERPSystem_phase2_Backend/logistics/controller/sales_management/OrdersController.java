package com.megazone.ERPSystem_phase2_Backend.logistics.controller.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.orders.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/orders")
public class OrdersController {

    private final OrdersService ordersService;

    /**
     * 주문서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getOrders(@RequestBody(required = false) SearchDTO dto) {
        List<OrdersResponseDto> response = ordersService.findAllOrders(dto);

        if(response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("주문서가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }
    /**
     * 주문서 상세 조회

     * @param id 주문서 ID
     * @return 주문서 상세 정보
     */
    @PostMapping("/{id}")
    public ResponseEntity<OrdersResponseDetailDto> getOrdersDetails(@PathVariable("id") Long id) {

        return ordersService.findOrdersDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 주문서 등록
     * @param createDto 주문서 생성 정보
     * @return 생성된 주문서 정보
     */
    @PostMapping("/create")
    public ResponseEntity<?> createOrders(@RequestBody OrdersCreateDto createDto) {
        OrdersResponseDetailDto savedOrders = ordersService.createOrders(createDto);
        return savedOrders != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedOrders) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문서 생성에 실패했습니다.");
    }

    /**
     * 주문서 수정
     * @param id 주문서 ID
     * @param updateDto 수정할 주문서 정보
     * @return 수정된 주문서 정보
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<OrdersResponseDetailDto> updateOrders(@PathVariable("id") Long id, @RequestBody OrdersCreateDto updateDto) {
        OrdersResponseDetailDto updatedOrders = ordersService.updateOrders(id, updateDto);
        return ResponseEntity.ok(updatedOrders);
    }

    /**
     * 주문서 삭제
     * @param id 주문서 ID
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrders(@PathVariable("id") Long id) {
        String result = ordersService.deleteOrders(id);
        return ResponseEntity.ok(result);
    }
}
