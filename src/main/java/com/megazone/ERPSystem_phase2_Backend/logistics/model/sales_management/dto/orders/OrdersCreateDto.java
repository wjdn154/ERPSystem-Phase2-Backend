package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders;


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
public class OrdersCreateDto {
    private LocalDate date;
    private LocalDate deliveryDate;
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private Long currencyId;
    private Long vatId;
    private String journalEntryCode;
    private String electronicTaxInvoiceStatus;
    private String remarks;
    private List<OrdersItemDetailDto> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrdersItemDetailDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal supplyPrice;
        private BigDecimal localAmount;  // 원화 금액
        private BigDecimal vat;
        private String remarks;
    }
}
