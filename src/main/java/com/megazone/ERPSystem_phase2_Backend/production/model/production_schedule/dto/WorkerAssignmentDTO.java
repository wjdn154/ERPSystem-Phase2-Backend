package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerAssignmentDTO {
    private String workerName;
    private String employeeNumber;
    private String workcenterCode; // 작업장코드
    private String workcenterName; // 작업장명
    private LocalDate assignmentDate; // 배정일
    private String shiftTypeName; // 교대유형명
    private String productionOrderName; // 연관 작업지시
}
