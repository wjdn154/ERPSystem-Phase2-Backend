package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.enums.WorkcenterType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private String factoryCode;
    private String factoryName;

    private Long processId;
    private String processCode;
    private String processName;

    private List<Long> equipmentIds;
    private List<String> equipmentNames; // 설비명들
    private List<String> modelNames; // 모델명들

    private List<Long> workerAssignmentIds; // 작업자 배치 ID 리스트만 포함 (WorkerAssignment)
    private Long todayWorkerCount; // 작업장의 오늘의작업자 인원수
    private List<String> todayWorkers;  // 작업장의 오늘의 작업자 이름 리스트 (WorkerAssignment)

//    // 항상 불러오는 오늘의 작업자 정보 설정 메서드
//    public void setTodayWorkers(List<WorkerAssignmentDTO> todayWorkersDTO) {
//        // null 이거나 비어있을 경우 "배정없음" 기본 값 설정
//        if (todayWorkersDTO == null || todayWorkersDTO.isEmpty()) {
//            this.todayWorkers = Collections.singletonList("배정없음");
//        } else {
//            // WorkerAssignmentDTO 리스트에서 작업자 이름과 사원 번호를 추출하여 저장
//            this.todayWorkers = todayWorkersDTO.stream()
//                    .map(dto -> dto.getWorkerName() + " (" + dto.getEmployeeNumber() + ")") // DTO에 작업자 이름과 사원 번호 포함
//                    .collect(Collectors.toList());
//        }
//    }

}


