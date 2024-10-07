package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    private Long inventoryId;                    // 기존 재고 ID (보내는 재고)
    private Long warehouseId;                    // 창고 ID
    private Long sendWarehouseLocationId;        // 보내는 위치 ID (기존 위치)
    private Long employeeId;                     // 작업자 ID
    private LocalDate transferDate;              // 이동 일자
    private List<ReceiveLocationInfo> receiveLocations;  // 여러 받는 위치 및 수량 정보 리스트
}
