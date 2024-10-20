package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.enums.QualityInspectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualityInspectionSaveDTO {

    private String inspectionCode;     //품질 검사 코드
    private String inspectionName;     //품질 검사 이름
    private String description;        //설명
    private QualityInspectionType qualityInspectionType;   //품질 검사 유형

    private Long workPerformanceId;         //작업실적 아이디
    private String workPerformanceName;     //작업실적 이름

    private String productCode;            //품목 코드
    private String productName;            //품목 명

}
