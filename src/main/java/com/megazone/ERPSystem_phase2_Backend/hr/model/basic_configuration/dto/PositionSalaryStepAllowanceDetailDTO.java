package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionSalaryStepAllowanceDetailDTO {
    private Long allowanceId;
    private String allowanceName;
    private BigDecimal amount;

    public static PositionSalaryStepAllowanceDetailDTO create(Long allowanceId, String allowanceName, BigDecimal amount) {
        return new PositionSalaryStepAllowanceDetailDTO(allowanceId, allowanceName, amount);
    }
}
