package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingProcessingRequestDTO {
    private Long inventoryId;              // 재고 ID
    private Long productId;                // 제품 ID
    private Long shippingOrderDetailId;     // 출하 지시서 상세 ID
    private String shippingDate;         // 출하 날짜
//    private Long shippingNumber;            // 출하 번호
    private String productName;             // 제품명
    private Long shippingInventoryNumber;   // 출하 재고 번호
}
