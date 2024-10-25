package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingOrderDetailResponseDTO {

    private Long id;                   // 입고지시서 상세 ID
    private Long productId;            // 품목 ID
    private String productName;        // 품목 이름
    private Long receivingWarehouseId; // 입고 창고 ID
    private String receivingWarehouseName; // 입고 창고 이름
    // 입고 예정 수량
    private Integer scheduledQuantity;
    private String deliveryDate;    // 입고 예정 일자
    private String remarks;            // 비고

}

