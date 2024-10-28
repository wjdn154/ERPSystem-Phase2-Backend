package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 출하지시서 등록용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingOrderCreateDto {
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private LocalDate date;
    private LocalDate shippingDate;
    private String remarks;
    private List<ShippingOrderItemCreateDto> items;

    // 출하지시서 세부 항목 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingOrderItemCreateDto {
        private Long productId;
        private Integer quantity;
        private String remarks;
    }

}
