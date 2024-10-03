package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReceivingOrderResponseDto {
    private Long id;
    private String clientName;
    private String managerName;
    private String warehouseName;
    private String productName;
    private LocalDate date;
    private LocalDate deliveryDate;
    private Integer totalQuantity;
    private String status;
    private String remarks;
}
