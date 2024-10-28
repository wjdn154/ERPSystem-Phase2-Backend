package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 출하지시서 상세 조회용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingOrderResponseDetailDto {

    private Long id;
    private LocalDate date;
    private LocalDate deliveryDate;
    private String clientCode;
    private String clientName;
    private String managerCode;
    private String managerName;
    private String managerContact;
    private String warehouseCode;
    private String warehouseName;
    private String remarks;
    private String status;
    private List<ShippingOrderItemDetailDto> receivingOrderDetails;

    /**
     * 출하지시서 상세 항목에 대한 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingOrderItemDetailDto {
        private String productName;
        private String productCode;
        private String standard;
        private Integer quantity;
        private String remarks;

    }
}
