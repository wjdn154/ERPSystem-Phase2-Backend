package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SalePlanResponseDetailDto {
    private Long id;
    private Long clientId;
    private String clientCode;
    private String clientName;
    private Long managerId;
    private String managerCode;
    private String managerName;
    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String productName;
    private String remarks;
    private String status;

    private List<SalePlanItemDetailDto> purchaseDetails;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePlanItemDetailDto {
        private Long productId;
        private String productName;
        private String productCode;
        private String standard;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal expectedSales;
        private String remarks;
    }
}