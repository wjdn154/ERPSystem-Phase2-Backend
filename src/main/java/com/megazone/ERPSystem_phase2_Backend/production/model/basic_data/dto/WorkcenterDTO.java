package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkcenterDTO {
    private Long id;
    private String code;
    private WorkcenterType workcenterType;
    private String name;
    private String description;
    private Boolean isActive;
    private WarehouseResponseDTO factoryCode; // Factory의 코드 (Warehouse의 정보 중 일부)
    private ProcessDetailsDTO processCode; // ProcessDetails의 코드
    private List<EquipmentDataDTO> equipmentList = new ArrayList<>();
    private List<WorkerAssignmentDTO> workerAssignments = new ArrayList<>();

}
