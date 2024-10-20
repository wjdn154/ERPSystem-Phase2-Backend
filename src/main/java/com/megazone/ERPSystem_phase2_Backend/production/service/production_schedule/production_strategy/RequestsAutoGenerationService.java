package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.production_strategy;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Orders;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_request.ProductionRequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * // TODO 자동생성, 승인반려 등 책임 분리할지 고민
 */

@Service
@RequiredArgsConstructor
public class RequestsAutoGenerationService {

    private final ProductionRequestsRepository productionRequestsRepository;

    // 추후 영업관리 추가 시 변경
    public ProductionRequest createProductionRequestFromSalesOrder(Long salesOrderId, Long productId, BigDecimal quantity, Long requesterId) {
        // 연관된 엔티티들은 ID만 이용해 생성 TODO
//        Orders salesOrder = new Orders();
//        salesOrder.setId(salesOrderId);  // Sales Order ID 설정

        Product product = new Product();
        product.setId(productId);  // Product ID 설정

        Employee requester = new Employee();
        requester.setId(requesterId);  // Requester (Employee) ID 설정

        // ProductionRequest 엔티티 생성
        ProductionRequest productionRequest = ProductionRequest.builder()
                .name("생산 요청 for Sales Order " + salesOrderId)
                .isConfirmed(false)  // 자동 생성된 요청은 기본적으로 승인되지 않은 상태로 설정
                .requestDate(LocalDate.now())  // 현재 날짜로 요청일자 설정
                .requestQuantity(quantity)  // 요청 수량 설정
                .confirmedQuantity(BigDecimal.ZERO)  // 확정 수량은 0으로 임시 설정 TODO
                .product(product)  // Product 엔티티 설정
//                .salesOrder(salesOrder)  // Sales Order 엔티티 설정
                .requester(requester)  // Requester (Employee) 엔티티 설정
                .remarks("자동 생성")  // 비고란에 자동 생성 표시
                .build();

        // 저장 후 반환
        return productionRequestsRepository.save(productionRequest);
    }
}

