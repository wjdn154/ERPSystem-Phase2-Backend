package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowanceAmountDTO {
    private BigDecimal amount;
    private Long allowanceId;
}