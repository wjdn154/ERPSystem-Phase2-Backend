package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseLocationRequestDTO {

    private Long warehouseId;      // 창고 ID (연관된 Warehouse)
    private String locationName;   // 창고 위치 이름
    private boolean active;      // 활성화 여부

    // WarehouseLocation 엔티티로 변환하는 메서드
    public WarehouseLocation mapToEntity(Warehouse warehouse) {
        return WarehouseLocation.builder()
                .warehouse(warehouse)
                .locationName(this.locationName)
                .isActive(this.active)
                .build();
    }
}
