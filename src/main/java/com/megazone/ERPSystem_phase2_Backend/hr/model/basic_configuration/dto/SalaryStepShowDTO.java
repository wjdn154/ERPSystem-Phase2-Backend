package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.SalaryStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryStepShowDTO {
    private Long id;
    private String code;
    private String name;
    private String description;

    public static SalaryStepShowDTO create(SalaryStep salaryStep) {
        return new SalaryStepShowDTO(
                salaryStep.getId(),
                salaryStep.getCode(),
                salaryStep.getName(),
                salaryStep.getDescription()
        );
    }
}
