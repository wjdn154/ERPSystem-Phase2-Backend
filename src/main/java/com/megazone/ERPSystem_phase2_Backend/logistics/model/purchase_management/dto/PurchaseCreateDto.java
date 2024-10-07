package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDetailDto.PurchaseItemDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 구매서 등록용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseCreateDto {
    private LocalDate date;
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private Long currencyId;
    private Long vatId;
    private String journalEntryCode;
    private String remarks;
    private List<PurchaseItemDetailDto> items;

    // 구매서 세부 항목 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseItemDetailDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal supplyPrice;
        private BigDecimal localAmount;  // 원화 금액
        private BigDecimal vat;
        private String remarks;
    }
}
