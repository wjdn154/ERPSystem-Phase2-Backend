package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
public class OrdersResponseDto {
    private Long id;
    private String clientName;
    private LocalDate date;
    private LocalDate deliveryDate;
    private String productName;
    private String warehouseName;
    private BigDecimal totalPrice;
    private Integer totalQuantity;
    private String vatName;
    private String status;
}
