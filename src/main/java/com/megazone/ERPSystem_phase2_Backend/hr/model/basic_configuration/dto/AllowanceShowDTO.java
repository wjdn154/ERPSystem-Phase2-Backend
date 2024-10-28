package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.Allowance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllowanceShowDTO {
    private Long id;
    private String code;
    private String name;
    private String description;

    public static AllowanceShowDTO create(Allowance allowance) {
        return new AllowanceShowDTO(
                allowance.getId(),
                allowance.getCode(),
                allowance.getName(),
                allowance.getDescription()
        );
    }
}
