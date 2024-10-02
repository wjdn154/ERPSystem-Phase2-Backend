package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefectCategoryDTO {
    
    private Long id;
    private String defectCategoryCode;   //불량군 코드
    private String defectCategoryName;   //불량군 이름
    private String isUsed;              //사용 여부
    
    private String defectTypeName;   //불량 유형 이름

}
