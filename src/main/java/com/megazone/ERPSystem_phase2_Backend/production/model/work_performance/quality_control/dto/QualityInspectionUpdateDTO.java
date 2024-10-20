package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.enums.QualityInspectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**inspectedProduct 리스트는 수정하지 않음.
 * 생산실적 수량을 들고오기 때문에 수량은 그대로고 바뀌더라도 개별 품질검사 결과가 바뀌는것이기 때문에
 * 굳이 여기서 수정하지는 않음.
 * 개별 품질검사 수정에서 수정할 예정 -> 자동 반영
 *
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualityInspectionUpdateDTO {

    private Long id;
    private String inspectionCode;     //품질 검사 코드
    private String inspectionName;     //품질 검사 이름
    private String description;        //설명
    private QualityInspectionType qualityInspectionType;   //품질 검사 유형

    private Long workPerformanceId;         //작업실적 아이디
    private String workPerformanceName;     //작업실적 이름

    private String productCode;            //품목 코드
    private String productName;            //품목 명

}
