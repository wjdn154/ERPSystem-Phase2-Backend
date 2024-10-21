package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspectionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryInspectionStatusResponseListDTO {
    private Long id;  // 실사 항목 ID
    private String inspectionNumber;  // "2024/09/30 - 1" 형식의 전표 번호
    private String productCode;  // 품목 코드
    private String productName;  // 품목 이름
    private String standard;  // 규격
    private String warehouseName;  // 창고명 (예: 본사창고)
    private Long bookQuantity;  // 장부상의 수량
    private Long actualQuantity;  // 실사된 수량
    private Long differenceQuantity;  // 차이
    private String comments;  // 비고

    public static InventoryInspectionStatusResponseListDTO mapToDTO(InventoryInspectionDetail inspectionDetail) {
        return new InventoryInspectionStatusResponseListDTO(
                inspectionDetail.getId(),
                inspectionDetail.getInventoryInspection().getInspectionDate() + " - " + inspectionDetail.getInventoryInspection().getInspectionNumber(),
                inspectionDetail.getProductCode(),
                inspectionDetail.getProductName(),
                inspectionDetail.getProduct().getStandard(),
                inspectionDetail.getWarehouseLocation().getWarehouse().getName(),
                inspectionDetail.getInventory().getQuantity(),
                inspectionDetail.getInventory().getQuantity(),
                inspectionDetail.getDifferenceQuantity(),
                inspectionDetail.getComment()
        );
    }
}
