package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProductionRequestType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderDTO {

    private Long id;
    private String name; // 작업지시명

    private ProgressType progressType;
    private ProductionRequestType productionRequestType;

    private List<WorkerAssignmentDTO> workerAssignments; // 작업자 배정 DTO 리스트

//    private List<WorkPerformanceDTO> workPerformances; // 작업 실적 DTO 리스트

    @Builder.Default
    private Boolean closed = false;

    private String remarks; // 추가 설명 또는 비고

    private Boolean confirmed;

    private LocalDateTime startDateTime; // 작업 시작 날짜 및 시간
    private LocalDateTime endDateTime; // 작업 종료 날짜 및 시간

    private String processDetailsCode;
    private String processDetailsName;

    private String workcenterCode;
    private String workcenterName;


}
