package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.google.common.base.Supplier;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.PositionSalaryStep;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.PositionSalaryStepAllowance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionSalaryStepShowDTO {
    private Long positionSalaryStepId;
    private Long salaryStepId;
    private String salaryStepName;
    private List<PositionSalaryStepAllowanceDetailDTO> allowances;
    private BigDecimal totalAllowance;
    private LocalDate startDate;
    private LocalDate endDate;

    public static PositionSalaryStepShowDTO create(PositionSalaryStepDTO dto) {
        return new PositionSalaryStepShowDTO(
                dto.getPositionSalaryStepId(),
                dto.getSalaryStepId(),
                dto.getSalaryStepName(),
                null,
                BigDecimal.ZERO,
                dto.getStartDate(),
                dto.getEndDate()
        );
    }
}
