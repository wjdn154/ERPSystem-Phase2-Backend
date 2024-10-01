package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryInspectionRequestDTO {
    private LocalDate inspectionDate; // 실사 일자
    private Long warehouseId; // 창고 ID (창고 선택)
    private Long employeeId; // 담당자 ID
    private String comment; // 코멘트 (비고)
    private List<InventoryInspectionDetailRequestDTO> details; // 실사 품목 리스트
}
