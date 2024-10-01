package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.HazardLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 유해물질 등록, 수정, 삭제 dto
 * 해당 자재의 유해물질 리스트 등록, 삭제 dto
 * -> front에서는 코드,이름,위험등급만 출력
 * */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HazardousMaterialDTO {

    private Long id;
    private String hazardousMaterialCode;    //유해물질 코드
    private String hazardousMaterialName;    //유해물질 이름
    private HazardLevel hazardLevel;         //위험등급
    private String description;              //설명

}
