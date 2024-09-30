package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PurchaseOrderResponseDto {
    private Long id;
    private String clientName;
    private String managerName;
    private String productName;
    private LocalDate date;
    private LocalDate deliveryDate;
    private Integer totalQuantity;
    private Double totalPrice;
    private String status;
}
