package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkerAssignmentDTO {

    private String workcenterCode;     //worker 의 workerAssignment 의 작업장 코드
    private String workcenterName;     //worker 의 workerAssignment 의 작업장 이름
    private String assignmentDate;       //작업배치의 작업배치 날짜
}
