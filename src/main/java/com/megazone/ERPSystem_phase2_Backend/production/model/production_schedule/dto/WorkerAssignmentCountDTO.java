package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerAssignmentCountDTO {
    private String workcenterCode;
    private String workcenterName;
    private Long workerCount;
}
