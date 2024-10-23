package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.ShipmentProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentProductListResponseDTO {
    private Long id;
    private Long shipmentId;
    private String shipmentNumber; // "일자 - 전표 번호" 형식의 전표 번호 (예: "2024/09/02 - 1")
    private String productName; // 첫 번째 품목명과 추가 품목 개수 (예: "즉석밥 외 2건")
    private Long quantity; // 수량 합계
    private String warehouseName; // 창고명
    private String clientName; // 거래처명
    private String contactInfo; // 거래처 연락처
    private String comment; // 비고

    public static ShipmentProductListResponseDTO mapToDto(ShipmentProduct shipmentProduct) {
        return new ShipmentProductListResponseDTO(
                shipmentProduct.getId(),
                shipmentProduct.getShipment().getId(),
                shipmentProduct.getShipment().getShipmentDate() + " - " + shipmentProduct.getShipment().getShipmentNumber(),
                shipmentProduct.getProduct().getName(),
                shipmentProduct.getQuantity(),
                shipmentProduct.getShipment().getWarehouse().getName(),
                shipmentProduct.getShipment().getClientName(),
                shipmentProduct.getShipment().getContactInfo(),
                shipmentProduct.getShipment().getComment()
        );
    }
}
