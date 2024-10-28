package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingOrderProcessRequestListDTO {
    private Long receivingOrderDetailId;  // 입고 지시서 상세 ID
    private String receivingDate;      // 입고 일자
    private List<ReceivingOrderProcessRequestDTO> requests;  // 여러 입고처리 요청 리스트
}
