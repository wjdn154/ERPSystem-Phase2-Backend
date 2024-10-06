package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseLocationResponseDTO {

    private Long id;                // 창고 위치 ID
    private Long warehouseId;        // 창고 ID
    private String locationName;     // 창고 위치 이름
    private boolean active;        // 활성화 여부

    public static WarehouseLocationResponseDTO mapToDTO(WarehouseLocation location) {
        return new WarehouseLocationResponseDTO(
                location.getId(),
                location.getWarehouse().getId(),   // Warehouse ID 추출
                location.getLocationName(),
                location.isActive()
        );
    }
}
