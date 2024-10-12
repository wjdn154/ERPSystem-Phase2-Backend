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
public class QualityInspectionDetailDTO {

    private Long id;
    private String inspectionCode;     //품질 검사 코드
    private String inspectionName;     //품질 검사 이름
    private String description;        //설명
    private Boolean isPassed;          //품질 통과 여부 
    private QualityInspectionType qualityInspectionType;   //품질 검사 유형 
    private Long workPerformanceId;         //작업실적 아이디
    private String workPerformanceName;     //작업실적 이름

    private List<DefectCategoryDTO> defectCategory;    //불량품 리스트
    private List<InboundRegistrationDTO> inboundRegistration;   //품질검사에 따른 제품 입고 리스트
}
