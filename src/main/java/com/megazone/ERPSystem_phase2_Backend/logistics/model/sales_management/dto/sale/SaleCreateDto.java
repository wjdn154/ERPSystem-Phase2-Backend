package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateDto {
    private LocalDate date;
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private Long currencyId;
    private Long vatId;
    private String journalEntryCode;
    private Boolean accountingReflection;
    private String remarks;
    private List<SaleItemDetailDto> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleItemDetailDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal supplyPrice;
        private BigDecimal localAmount;  // 원화 금액
        private BigDecimal vat;
        private String remarks;
    }
}
