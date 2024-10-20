package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.enums.WorkStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**작업 실적 리스트 dto
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkPerformanceListDTO {

    private Long id;   //아이디
    private String name;    //이름
    private Long actualQuantity;    //실제 생산량
    private BigDecimal workCost;          //작업 비용
    private WorkStatus workStatus;        //작업 상태

    private String workDailyReportCode;    //일별 보고서 코드
    private String workDailyReportTitle;    //일별 보고서 제목

    private Long productionOrderId;       //작업지시 아이디
    private String productionOrderName;   //작업지시 이름

    private String productCode;         //품목 코드
    private String productName;         //품목 이름

}
