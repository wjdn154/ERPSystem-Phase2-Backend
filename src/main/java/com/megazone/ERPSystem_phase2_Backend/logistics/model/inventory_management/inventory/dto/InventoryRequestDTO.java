package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDTO {

    private Long inventoryNumber;      // 재고 번호
    private Long warehouseId;           // 창고 id
    private Long productId;             // 품목 id
    private Long locationId;            // 위치 id
    private String standard;            // 규격
    private Long quantity;              // 재고 수량
}
