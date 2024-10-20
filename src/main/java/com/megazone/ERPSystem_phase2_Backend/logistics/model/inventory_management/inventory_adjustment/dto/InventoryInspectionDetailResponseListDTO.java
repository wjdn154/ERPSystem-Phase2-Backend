package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryInspectionDetailResponseListDTO {
    private Long id;  // 실사 항목 ID
    private String productCode;  // 품목 코드
    private String productName;  // 품목 이름
    private String standard;  // 규격
    private Long bookQuantity;  // 장부상의 수량
    private Long actualQuantity;  // 실사된 수량
    private Long differenceQuantity;  // 차이
    private String comments;  // 비고
}
