package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspectionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryInspectionDetailResponseDTO {
    private Long id;  // 실사 항목 ID
    private Long inventoryId;
    private Long warehouseLocationId;  // 품목이 위치한 창고 위치 ID (추가)
    private String warehouseLocationName;  // 품목이 위치한 창고 위치 이름 (추가)
    private Long productId;  // 품목 ID
    private String productCode;  // 품목 코드
    private String productName;  // 품목 이름
    private String standard;
    private String unit;
    private Long actualQuantity;  // 실사된 수량
    private String comments;

    public static InventoryInspectionDetailResponseDTO mapToDto(InventoryInspectionDetail inventoryInspectionDetail) {
        return new InventoryInspectionDetailResponseDTO(
                inventoryInspectionDetail.getId(),
                inventoryInspectionDetail.getInventory().getId(),
                inventoryInspectionDetail.getWarehouseLocation().getId(),
                inventoryInspectionDetail.getWarehouseLocation().getLocationName(),
                inventoryInspectionDetail.getProduct().getId(),
                inventoryInspectionDetail.getProduct().getCode(),
                inventoryInspectionDetail.getProduct().getName(),
                inventoryInspectionDetail.getStandard(),
                inventoryInspectionDetail.getUnit(),
                inventoryInspectionDetail.getActualQuantity(),
                inventoryInspectionDetail.getComment()
        );
    }
}
