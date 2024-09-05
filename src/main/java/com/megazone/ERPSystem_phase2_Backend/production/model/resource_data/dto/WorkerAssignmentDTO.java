package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

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
    private Long id;
    private Long workerId; // 작업자
    private String workcenterCode; // 작업장
    private LocalDate assignmentDate; // 배정일
    private String shift; // 교대
    private Long workOrderId; // 연관 작업지시
}
