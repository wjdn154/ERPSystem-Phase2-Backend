package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeductionDTO {

    private Long id;                // 공제 ID
    private String name;            // 공제 이름
    private BigDecimal amount;      // 공제 금액
    private String type;            // 공제 유형
}