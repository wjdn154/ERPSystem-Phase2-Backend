package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingScheduleResponseDTO {

    private Long id;                     // ReceivingSchedule ID
    private Long receivingOrderDetailId;  // 입고 지시서 상세 ID
    private Long warehouseLocationId;     // 창고 위치 ID
    private String warehouseLocationName;
    private String productName;           // 품목 이름
    private Long pendingInventoryNumber; // 입고 대기 재고 번호
    private Integer pendingQuantity;      // 입고 대기 수량
    private String receivingDateNumber;     // 입고 일자 번호

}
