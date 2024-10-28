package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingOrderProcessRequestDTO {

    private Long warehouseLocationId;     // 입고될 창고 위치 ID
    private String productName;           // 품목 이름
    private Long pendingInventoryNumber; // 입고 대기 재고 번호
    private Integer pendingQuantity;      // 지시 수량 (입고 대기 수량으로 사용)

}
