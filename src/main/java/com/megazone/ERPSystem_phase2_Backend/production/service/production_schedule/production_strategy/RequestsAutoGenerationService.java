package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.production_strategy;

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
    public ProductionRequest createProductionRequestFromSalesOrder(String salesOrder, String product, BigDecimal quantity, String requester) {
        ProductionRequest productionRequest = ProductionRequest.builder()
                .name("생산 요청 for " + salesOrder)
                .isConfirmed(false)
                .requestDate(LocalDate.now())
                .requestQuantity(quantity)
                .confirmedQuantity(BigDecimal.ZERO)
                .product(product)
                .salesOrder(salesOrder)
                .requester(requester)
                .remarks("자동 생성")
                .build();

        return productionRequestsRepository.save(productionRequest);
    }

}
