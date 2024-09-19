package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.HazardLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HazardousMaterialDTO {

    private String hazardousMaterialCode;    //유해물질 코드
    private String hazardousMaterialName;    //유해물질 이름
    private HazardLevel hazardLevel;         //위험등급
    private String description;              //설명

}
