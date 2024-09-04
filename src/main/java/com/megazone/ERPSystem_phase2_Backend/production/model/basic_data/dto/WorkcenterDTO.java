package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
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

    private WarehouseType warehouseType; // TYPE: FACTORY OR OUTSOURCING_FACTORY
    private String factoryCode; // 공장 Code만 포함 (Warehouse)
    private String processCode; // 생산공정 Code만 포함 (ProcessDetails)
    private List<Long> equipmentIds; // 설비 ID 리스트만 포함 (EquipmentData)
    private List<Long> workerAssignmentIds; // 작업자 배치 ID 리스트만 포함 (WorkerAssignment)
    private List<String> todayWorkers;  // 작업자 이름 리스트 (WorkerAssignment)
}

