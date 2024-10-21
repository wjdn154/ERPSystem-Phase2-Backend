package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryInspectionDetailRequestDTO {
    private Long productId;  // 품목 ID
    private Long inventoryId;
    private Long warehouseLocationId; // 품목이 저장된 창고 위치 ID 추가
    private String productCode;  // 품목 코드
    private String productName;  // 품목 이름
    private String standard;
    private String unit;
    private Long actualQuantity;  // 실사된 수량
    private String comment;  // 품목 코멘트 (옵션)
}
