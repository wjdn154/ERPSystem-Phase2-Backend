package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryAdjustmentDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryAdjustmentDetailDTO {
    private Long id;
    private String productCode;
    private String productName;
    private String standard;
    private String unit;
    private Long bookQuantity;
    private Long adjustmentQuantity;
    private String comment;

    public static InventoryAdjustmentDetailDTO mapToDTO(InventoryAdjustmentDetail detail) {
        return new InventoryAdjustmentDetailDTO(
                detail.getId(),
                detail.getProductCode(),
                detail.getProductName(),
                detail.getStandard(),
                detail.getUnit(),
                detail.getBookQuantity(),
                detail.getAdjustmentQuantity(),
                detail.getComment()
        );
    }
}
