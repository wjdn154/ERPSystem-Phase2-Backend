package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.LongTermCareInsurancePension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LongTermCareInsuranceShowDTO {
    private Long id;
    private String code;
    private String description;

    public static LongTermCareInsuranceShowDTO create(LongTermCareInsurancePension pension) {
        return new LongTermCareInsuranceShowDTO(
                pension.getId(),
                pension.getCode(),
                pension.getDescription()
        );
    }
}
