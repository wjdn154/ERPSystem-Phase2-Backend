package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ListWorkerAttendanceDTO {

    private Long id;
    private String employeeNumber;     //employee 의 사원번호
    private List<WorkerAttendanceDTO> workerAttendance;
    private List<WorkerAssignmentDTO> workerAssignment;

}
