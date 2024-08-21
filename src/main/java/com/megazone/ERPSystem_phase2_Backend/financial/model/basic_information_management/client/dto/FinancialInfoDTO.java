package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * 재무 정보 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FinancialInfoDTO {
    private BigDecimal collateralAmount; // 담보 설정액
    private BigDecimal creditLimit; // 여신 한도액
}