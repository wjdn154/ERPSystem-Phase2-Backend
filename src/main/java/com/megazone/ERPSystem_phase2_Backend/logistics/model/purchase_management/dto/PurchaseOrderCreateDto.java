package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal exchangeRate; // 사용자가 환율을 변경한 경우에만 전달
    private LocalDate date;
    private LocalDate deliveryDate;
    private Long vatId;
    private String journalEntryCode;
    private String electronicTaxInvoiceStatus;
    private String remarks;
    private List<PurchaseOrderItemCreateDto> items;

    // 발주서 세부 항목 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseOrderItemCreateDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal supplyPrice;
        private BigDecimal localAmount; // 원화 금액
        private BigDecimal vat;
        private String remarks;
    }
}
