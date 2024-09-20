package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import java.time.LocalDate;
import java.util.List;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.State;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseRequestDto {
    private Long id;
    private Long clientId;
    private Long employeeId;
    private Long warehouseId;
    private Long currencyId;
    private Long productId;
    private Integer quantity;
    private Double supplyPrice;
    private Boolean vatType;
    private Double vat;
    private LocalDate date;
    private LocalDate deliveryDate;
    private String remarks;
    private State status;
    private List<PurchaseRequestDetailDto> purchaseRequestDetails;
}