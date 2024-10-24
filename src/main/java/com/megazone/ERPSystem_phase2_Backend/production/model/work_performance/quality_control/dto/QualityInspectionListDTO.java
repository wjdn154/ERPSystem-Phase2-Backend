package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.enums.QualityInspectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualityInspectionListDTO {

    private Long id;
    private String inspectionCode;
    private String inspectionName;
    private QualityInspectionType qualityInspectionType;  //품질 검사 유형

    private Long workPerformanceId;        //작업실적 아이디

    private String productCode;          //품목 코드
    private String productName;          //품목 이름

    private BigDecimal totalQuantity;        //총 수량 (workperformance-actualQuantity)
    private BigDecimal defectiveQuantity;    //불량품 수량 (inspectedProduct-defectCount)
    private BigDecimal passedQuantity;       //통과된 제품 수량
}


/**검사번호, 품목, 수량, 적격, 부적격, 종결 여부?
 * */