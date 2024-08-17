package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long id;
    private String businessPhone; // 사업장 전화번호
    private String fax; // 팩스번호
}
