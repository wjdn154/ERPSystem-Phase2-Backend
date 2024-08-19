package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.CorporateKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateKindDTO {
    private String code; // 법인종류 코드
    private String kind; // 법인종류
    private String description; // 법인종류 설명

    public CorporateKindDTO(CorporateKindDTO corporateKind) {
        this.code = corporateKind.getCode();
        this.kind = corporateKind.getKind();
        this.description = corporateKind.getDescription();
    }

    public CorporateKindDTO(CorporateKind corporateKind) {
        this.code = corporateKind.getCode();
        this.kind = corporateKind.getKind();
        this.description = corporateKind.getDescription();
    }
}
