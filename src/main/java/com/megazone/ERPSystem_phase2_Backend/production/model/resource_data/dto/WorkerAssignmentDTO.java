package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;

import java.util.List;

public class WorkerAssignmentDTO {
    private WorkerDTO worker;
    private WorkcenterDTO workcenters; // 작업장 리스트
    private String assignmentDate;
    private String shift;
}
