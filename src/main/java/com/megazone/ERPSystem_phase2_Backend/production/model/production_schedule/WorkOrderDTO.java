package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerAssignmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderDTO {

    private Long id; // 고유 ID

    private String name; // 작업지시명

    private Long planOfMakeToOrderId; // 생산 주문 계획 ID (Nullable)

    private Long planOfMakeToStockId; // 생산 재고 계획 ID (Nullable)

    private List<WorkerAssignmentDTO> workerAssignments; // 작업자 배정 DTO 리스트

//    private List<WorkPerformanceDTO> workPerformances; // 작업 실적 DTO 리스트

    private String remarks; // 추가 설명 또는 비고
}
