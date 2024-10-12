package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectedProductDetailDTO {

    private Long id;
    private String qualityInspectionCode;  //품질검사 코드
    private String inspectionName;         //품질검사 명
    private String productCode;   //검사된 제품의 코드 (품목코드). qualityInspection-product-productCode
    private String productName;   //검사된 제품의 이름 (품목 명). qualityInspection-product-productCode
    private String isPassed;      //개별 제품의 통과 여부

    private String defectCategoryCode;    //불량군 코드 (있는 경우에만)
    private String defectCategoryName;    //불량군 이름 (있는 경우에만)
    private Long defectCount;              //불량 수량  (있는 경우에만)

}
