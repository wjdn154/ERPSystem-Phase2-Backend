package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDTO {

    private Long id;                   // 재고 id
    private Long inventoryNumber;      // 재고 번호
    private String productCode;        // 품목 코드
    private String productName;        // 품목명
    private String standard;           // 규격
    private Long quantity;             // 재고 수량
    private String warehouseName;      // 창고 이름
    private String locationName;       // 위치 이름

    // Inventory 엔티티를 DTO로 변환하는 메서드
    public static InventoryResponseDTO mapToDTO(Inventory inventory) {
        return new InventoryResponseDTO(
                inventory.getId(),  // 재고 id
                inventory.getInventoryNumber(),
                inventory.getProduct().getCode(),  // 품목 코드
                inventory.getProduct().getName(),  // 품목명
                inventory.getStandard(),  // 규격
                inventory.getQuantity(),  // 재고 수량
                inventory.getWarehouse().getName(),  // 창고 이름
                inventory.getWarehouseLocation().getLocationName()  // 위치 이름
        );
    }
}
