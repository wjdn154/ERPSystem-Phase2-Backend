package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferProductRequestDTO {
    private Long productId;    // 상품 ID
    private Integer quantity;  // 상품 수량
    private String comment;    // 비고사항 (nullable)
}
