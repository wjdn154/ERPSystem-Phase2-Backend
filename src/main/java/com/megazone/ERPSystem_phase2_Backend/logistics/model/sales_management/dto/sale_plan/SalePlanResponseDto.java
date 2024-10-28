package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SalePlanResponseDto {
    private Long id;
    private String clientName;
    private String managerName;
    private String warehouseName;
    private String productName;
    private LocalDate date;
    private LocalDate expectedSaleDate;
    private Integer totalQuantity;
    private BigDecimal totalExpectedSales;

}
