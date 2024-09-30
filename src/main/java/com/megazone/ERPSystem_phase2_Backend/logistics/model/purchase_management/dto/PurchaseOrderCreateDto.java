package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 발주서 등록용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderCreateDto {
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private Long currencyId;
    private Double exchangeRate; // 사용자가 환율을 변경한 경우에만 전달
    private LocalDate date;
    private LocalDate deliveryDate;
    private Boolean vatType;
    private List<PurchaseOrderItemCreateDto> items;
    private String remarks;

    // 발주서 세부 항목 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseOrderItemCreateDto {
        private Long productId;
        private Integer quantity;
        private Double price; // 사용자가 단가를 변경한 경우에만 전달
        private Double supplyPrice;
        private Double localAmount; // 원화 금액
        private Double vat;
        private String remarks;
    }
}
