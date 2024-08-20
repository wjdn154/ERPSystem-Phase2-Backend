package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.CorporateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateTypeDTO {
    private String code; // 법인구분 코드
    private String type; // 법인구분
    private String description; // 법인구분 설명

    public CorporateTypeDTO(CorporateTypeDTO corporateType) {
        this.code = corporateType.getCode();
        this.type = corporateType.getType();
        this.description = corporateType.getDescription();
    }

    public CorporateTypeDTO(CorporateType corporateType) {
        this.code = corporateType.getCode();
        this.type = corporateType.getType();
        this.description = corporateType.getDescription();
    }
}
