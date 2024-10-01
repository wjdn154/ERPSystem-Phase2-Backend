package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.enums.QualityInspectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualityInspectionListDTO {

    private Long id;
    private String inspectionCode;
    private String inspectionName;
    private String description;
    private Boolean isPassed;
    private QualityInspectionType qualityInspectionType;
    private Long workPerformanceId;
    private String workPerformanceName;

}
