package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequestResponseDto {
    private Long id;
    private String clientName;
    private String productName;
    private LocalDate date;
    private LocalDate deliveryDate;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String status;

    public PurchaseRequestResponseDto(Long id, String clientName, String productName, LocalDate date, LocalDate deliveryDate, Integer totalQuantity, BigDecimal totalPrice, State status) {
        this.id = id;
        this.clientName = clientName;
        this.productName = productName;
        this.date = date;
        this.deliveryDate = deliveryDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.status = status.toString();  // 상태를 문자열로 변환
    }
}
