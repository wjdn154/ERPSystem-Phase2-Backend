package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProductionRequestType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    // 추가된 필드들
    private Long mpsId; // MPS ID
    private Long processDetailsId; // 공정 세부 ID
    private Long processRoutingStepId; // 공정 라우팅 단계 ID

    private BigDecimal productionQuantity; // 생산 지시 수량

    private LocalDateTime actualStartDateTime; // 실제 작업 시작 날짜 및 시간
    private LocalDateTime actualEndDateTime; // 실제 작업 종료 날짜 및 시간

    private BigDecimal actualProductionQuantity; // 실제 생산량

    private Long workers; // 계획된 작업 인원
    private Long actualWorkers; // 실제 작업 인원

}