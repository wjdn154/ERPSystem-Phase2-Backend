package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionSalaryStepDateDetailDTO {
    private LocalDate useStartDate;
    private LocalDate useEndDate;

    public static PositionSalaryStepDateDetailDTO create(LocalDate useStartDate, LocalDate useEndDate) {
        return new PositionSalaryStepDateDetailDTO(
                useStartDate,
                useEndDate
        );
    }
}
