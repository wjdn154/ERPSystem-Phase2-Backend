package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllowanceDTO {

        private Long id;                // 수당 ID
        private String name;            // 수당 이름
        private BigDecimal amount;      // 수당 금액
        private String type;            // 수당 유형
    }