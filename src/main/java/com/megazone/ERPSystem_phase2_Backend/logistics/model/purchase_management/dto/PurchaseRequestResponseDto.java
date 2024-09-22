package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.Builder;
import lombok.Data;

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

//    private Long id;
//    private LocalDate date;
//    private Long clientId;
//    private String clientCode;
//    private String clientName;
//    private Long productId;
//    private String productCode;
//    private String productName;
//    private Long managerId;
//    private String managerCode;
//    private String managerName;
//    private Long warehouseId;
//    private String warehouseCode;
//    private String warehouseName;
//    private Long currencyId;
//    private String currency;
//    private List<PurchaseRequestResponseDetailDto> details;
//    private LocalDate deliveryDate;
//    private String status;
//    private String remarks;
}
