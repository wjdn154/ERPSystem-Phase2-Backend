package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseRequestResponseDetailDto {

    private Long id;
    private Long purchaseRequestId;
    private Long productId;
    private String productCode;
    private String productName;
    private Integer quantity;
    private Double supplyPrice;
    private Double vat;
    private String remarks;
}
