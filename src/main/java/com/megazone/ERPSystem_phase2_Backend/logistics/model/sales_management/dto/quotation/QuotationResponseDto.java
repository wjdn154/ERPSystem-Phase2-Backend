package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
public class QuotationResponseDto {
    private Long id;
    private String clientName;
    private LocalDate date;
    private String productName;
    private String warehouseName;
    private BigDecimal totalPrice;
    private Integer totalQuantity;
    private String vatName;
    private String status;
}
