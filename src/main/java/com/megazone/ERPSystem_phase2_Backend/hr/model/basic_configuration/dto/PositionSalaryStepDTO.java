package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.PositionSalaryStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionSalaryStepDTO {
    private Long positionSalaryStepId; // 직급별 호봉 id
    private Long salaryStepId;
    private String salaryStepName;
    private Long allowanceId;
    private String allowanceName;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
}
