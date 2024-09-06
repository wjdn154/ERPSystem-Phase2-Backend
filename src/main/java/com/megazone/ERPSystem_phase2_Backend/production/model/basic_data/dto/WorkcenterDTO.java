package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
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
    private List<String> todayWorkers;  // 오늘의 작업자 이름 리스트 (WorkerAssignment)

    // 항상 불러오는 오늘의 작업자 정보 설정 메서드
    public void setTodayWorkers(List<String> todayWorkers) {
        // null 이거나 비어있을 경우 "배정없음" 기본 값 설정
        if (todayWorkers == null || todayWorkers.isEmpty()) {
            this.todayWorkers = Collections.singletonList("배정없음");
        } else {
            this.todayWorkers = todayWorkers;
        }
    }
}

