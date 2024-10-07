package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentProductRequestDTO {
    private Long productId; // 제품 ID
    private String productCode; // 제품 코드
    private String productName; // 제품명
    private String standard; // 규격
    private String unit; // 단위
    private Long quantity; // 수량
    private String comment; // 비고 (옵션)
}
