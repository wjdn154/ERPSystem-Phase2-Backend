package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.ShipmentProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ShipmentProductResponseDTO {
    private Long id; // 출하 제품 ID
    private String productCode; // 제품 코드
    private String productName; // 제품명
    private String standard; // 규격
    private String unit; // 단위
    private Long quantity; // 수량
    private String comment; // 비고

    public static ShipmentProductResponseDTO mapToDto(ShipmentProduct shipmentProduct) {
        return new ShipmentProductResponseDTO(
                shipmentProduct.getId(),
                shipmentProduct.getProduct().getCode(),
                shipmentProduct.getProduct().getName(),
                shipmentProduct.getStandard(),
                shipmentProduct.getUnit(),
                shipmentProduct.getQuantity(),
                shipmentProduct.getComment()
        );
    }
}
