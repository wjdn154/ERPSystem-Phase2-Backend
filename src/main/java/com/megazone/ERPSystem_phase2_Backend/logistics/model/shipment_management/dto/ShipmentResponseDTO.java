package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.Shipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponseDTO {
    private Long id; // 출하 전표 ID
    private String warehouseName; // 창고 이름
    private String employeeName; // 담당자 이름
    private String clientName; // 고객 이름
    private String contactInfo; // 고객 연락처
    private String warehouseAddress; // 창고 주소
    private LocalDate shipmentDate; // 출하 일자
    private Long shipmentNumber; // 출하 전표 번호
    private String comment; // 비고
    private List<ShipmentProductResponseDTO> products; // 출하 제품 리스트

    public static ShipmentResponseDTO mapToDto(Shipment shipment) {
        return new ShipmentResponseDTO(
                shipment.getId(),
                shipment.getWarehouse().getName(),
                shipment.getEmployee().getLastName() + shipment.getEmployee().getFirstName(),
                shipment.getClient().getPrintClientName(),
                shipment.getContactInfo() != null ? shipment.getContactInfo().getPhoneNumber() : null,
                shipment.getAddress() != null ? shipment.getAddress().getWarehouseAddress() : null,
                shipment.getShipmentDate(),
                shipment.getShipmentNumber(),
                shipment.getComment(),
                shipment.getProducts().stream()
                        .map(ShipmentProductResponseDTO::mapToDto)
                        .collect(Collectors.toList())
        );
    }
}
