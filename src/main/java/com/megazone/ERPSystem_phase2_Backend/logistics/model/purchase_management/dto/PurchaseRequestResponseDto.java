package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PurchaseRequestResponseDto {
    private Long id;
    private String clientName;
    private String productName;
    private LocalDate date;
    private LocalDate deliveryDate;
    private Integer totalQuantity;
    private Double totalPrice;
    private String status;
}
