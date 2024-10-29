package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.ShippingProcessing;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.enums.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingProcessingResponseDTO {
    private Long id;                     // 출고 처리 ID
    private Long inventoryId;            // 재고 ID
    private String warehouseLocationName;
    private Long productId;              // 제품 ID
    private Long shippingOrderDetailId;  // 출하 지시서 상세 ID
    private Integer quantity;
    private String shippingDate;      // 출하 날짜
    private String shippingNumber;         // 출하 번호
    private String productName;          // 제품명
    private Long shippingInventoryNumber;// 출하 재고 번호
    private ShippingStatus shippingStatus; // 출하 상태

    public static ShippingProcessingResponseDTO mapToDto(ShippingProcessing shippingProcessing) {
        return new ShippingProcessingResponseDTO(
                shippingProcessing.getId(),
                shippingProcessing.getInventory().getId(),
                shippingProcessing.getInventory().getWarehouseLocation().getLocationName(),
                shippingProcessing.getProduct().getId(),
                shippingProcessing.getShippingOrderDetail().getId(),
                shippingProcessing.getShippingOrderDetail().getQuantity(),
                shippingProcessing.getShippingDate().toString(),
                shippingProcessing.getShippingDate() + " - " + shippingProcessing.getShippingNumber(),
                shippingProcessing.getProductName(),
                shippingProcessing.getInventory().getInventoryNumber(),
                shippingProcessing.getShippingStatus()
        );
    }
}
