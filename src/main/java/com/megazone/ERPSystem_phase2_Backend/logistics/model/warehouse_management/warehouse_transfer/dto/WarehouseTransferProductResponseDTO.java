package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransferProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferProductResponseDTO {
    private Long id;          // 상품 ID
    private String productName;      // 상품 이름
    private Long quantity;        // 상품 수량
    private String comment;          // 비고사항 (nullable)

    public WarehouseTransferProductResponseDTO(WarehouseTransferProduct product) {
        this.id = product.getId();
        this.productName = product.getProduct().getName();
        this.quantity = product.getQuantity();
        this.comment = product.getComment();
    }
}
