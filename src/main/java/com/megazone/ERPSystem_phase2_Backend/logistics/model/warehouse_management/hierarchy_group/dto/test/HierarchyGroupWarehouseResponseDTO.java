package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.dto.test;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyGroupWarehouseResponseDTO {
    private String warehouseType;   // 창고 구분 (창고, 공장 등)
    private String warehouseCode;   // 창고 코드
    private String warehouseName;   // 창고 명

    public HierarchyGroupWarehouseResponseDTO(Warehouse warehouse) {
        this.warehouseType = warehouse.getWarehouseType().toString();
        this.warehouseCode = warehouse.getCode();
        this.warehouseName = warehouse.getName();
    }
}
