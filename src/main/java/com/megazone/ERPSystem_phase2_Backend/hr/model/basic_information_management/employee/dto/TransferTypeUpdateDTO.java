package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferTypeUpdateDTO {
    private Long transferTypeId;
    private String code;
    private String description;
}
