package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseRequestDetailDto {
    private Long id;

    // 발주 요청 ID
    private Long purchaseRequestId;

    // 품목 ID
    private Long productId;

    // 수량
    private Integer quantity;

    // 공급가액 (수량 * 단가)
    private Double supplyPrice;

    // 부가세
    private Double vat;

    // 비고
    private String remarks;
}
