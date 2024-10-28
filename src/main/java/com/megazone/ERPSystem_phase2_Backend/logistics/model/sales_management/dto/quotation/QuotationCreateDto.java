package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationResponseDetailDto.QuotationItemDetailDto;
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
public class QuotationCreateDto {
    private LocalDate date;
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private Long currencyId;
    private Long vatId;
    private String journalEntryCode;
    private String remarks;
    private List<QuotationItemDetailDto> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuotationItemDetailDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal supplyPrice;
        private BigDecimal localAmount;  // 원화 금액
        private BigDecimal vat;
        private String remarks;
    }
}
