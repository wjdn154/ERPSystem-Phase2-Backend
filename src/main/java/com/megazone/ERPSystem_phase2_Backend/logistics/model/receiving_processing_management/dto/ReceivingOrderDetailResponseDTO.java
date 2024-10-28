package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingOrderDetailResponseDTO {

    private Long id;                   // 입고지시서 상세 ID
    private Long receivingOrderId;
    private Long productId;            // 품목 ID
    private String productName;        // 품목 이름
    private Long receivingWarehouseId; // 입고 창고 ID
    private String receivingWarehouseName; // 입고 창고 이름
    private String date;               // 입고지시서 입력 일자
    private String deliveryDate;    // 입고 예정 일자
    private Integer quantity;          // 수량
    private Integer totalWaitingQuantity;  // 대기 수량 합
    private Integer unreceivedQuantity; // 미입고 수량
    private String remarks;            // 비고

}
