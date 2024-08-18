package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Representative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeDTO {
    private String name; // 대표자 이름
    private String idNumber; // 대표자 주민번호
    private Boolean isForeign ; // 대표자 외국인여부

    public RepresentativeDTO(RepresentativeDTO representative) {
        this.name = representative.getName();
        this.idNumber = representative.getIdNumber();
        this.isForeign = representative.getIsForeign();
    }

    public RepresentativeDTO(Representative representative) {
        this.name = representative.getName();
        this.idNumber = representative.getIdNumber();
        this.isForeign = representative.getIsForeign();
    }
}
