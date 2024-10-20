package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectedProductDTO {

    private Long id;
    private String isPassed;      //검사 통과 여부
    private String defectCategoryName;   //불량 유형 이름 (있는 경우)
    private Long defectCount;     //불량 수량 (있는 경우)
}
