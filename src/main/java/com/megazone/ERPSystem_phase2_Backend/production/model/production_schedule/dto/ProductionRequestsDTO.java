package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

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
public class ProductionRequestsDTO {
    private Long id; // PK

    private String name; // 생산의뢰명

    private Boolean isConfirmed; // 확정여부

    private LocalDate requestDate; // 요청일자

    private BigDecimal requestQuantity; // 요청수량

    private BigDecimal confirmedQuantity; // 확정수량

    private String product; // 제품

    private String salesOrder; // 영업 주문

    private String requester; // 요청자

    private String remarks; // 추가 설명
}
