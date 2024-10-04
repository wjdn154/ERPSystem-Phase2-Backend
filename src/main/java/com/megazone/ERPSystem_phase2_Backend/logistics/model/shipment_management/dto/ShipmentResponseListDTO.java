package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponseListDTO {
    private Long id;  // 출하 전표의 식별자 (id 값)
    private String shipmentNumber; // "일자 - 전표 번호" 형식의 전표 번호 (예: "2024/09/02 - 1")
    private String warehouseName; // 창고명
    private String firstProductName; // 첫 번째 품목명과 추가 품목 개수 (예: "즉석밥 외 2건")
    private Long totalQuantity; // 수량 합계
    private String clientName; // 거래처명
}
