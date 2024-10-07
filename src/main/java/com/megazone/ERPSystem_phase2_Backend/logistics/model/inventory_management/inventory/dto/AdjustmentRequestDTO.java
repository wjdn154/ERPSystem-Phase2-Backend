package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjustmentRequestDTO {

    private Long inventoryId;                 // 재고 ID (보내는 재고)
    private Long warehouseId;                 // 창고 ID
    private Long sendWarehouseLocationId;     // 보내는 위치 ID
    private Long receiveWarehouseLocationId;  // 받는 위치 ID
    private Long employeeId;                  // 작업자 ID
    private LocalDate adjustmentDate;         // 조정 일자
    private String adjustmentType;            // 조정 유형 (재고조정, 위치이동)
    private Long quantity;                    // 조정할 재고 수량
    private String summary;                   // 비고 (위치 이동 시, 위치 변경 요약 정보)
}

