package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class WorkerAssignmentSummaryDTO {
    private List<WorkerAssignmentDTO> details;
    private Map<String, Long> workcenterCounts;
}
