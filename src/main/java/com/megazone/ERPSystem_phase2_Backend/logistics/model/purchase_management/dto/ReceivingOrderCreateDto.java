package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 입고지시서 등록용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingOrderCreateDto {
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private LocalDate date;
    private LocalDate deliveryDate;
    private String remarks;
    private List<ReceivingOrderItemCreateDto> items;

    // 입고지시서 세부 항목 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReceivingOrderItemCreateDto {
        private Long productId;
        private Integer quantity;
        private String remarks;
    }

}
