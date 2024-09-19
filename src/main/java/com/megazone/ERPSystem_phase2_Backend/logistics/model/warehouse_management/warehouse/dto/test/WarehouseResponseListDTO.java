package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseResponseListDTO {
    private Long id; // 창고의 ID
    private String code; // 창고 코드
    private String name; // 창고명
    private String warehouseType; // 창고 타입
    private String productionProcess; // 생산공정명 (공장일 때만 사용)
    private Boolean isActive; // 사용 여부
}
