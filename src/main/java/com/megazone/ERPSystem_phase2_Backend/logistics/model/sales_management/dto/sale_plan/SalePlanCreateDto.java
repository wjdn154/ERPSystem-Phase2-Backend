package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalePlanCreateDto {
    private LocalDate date;
    private LocalDate expectedSalesDate;
    private Long clientId;
    private Long managerId;
    private Long warehouseId;
    private Long currencyId;
    private Long vatId;
    private String remarks;
    private List<SalePlanItemDetailDto> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePlanItemDetailDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal expectedSales;
        private String remarks;
    }
}
