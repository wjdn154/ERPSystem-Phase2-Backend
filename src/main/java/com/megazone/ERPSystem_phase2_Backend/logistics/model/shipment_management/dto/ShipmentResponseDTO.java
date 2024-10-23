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
    private Long warehouseId; // 창고 ID
    private String warehouseName; // 창고 이름
    private Long employeeId; // 담당자 ID
    private String employeeNumber; // 담당자 사번
    private String employeeName; // 담당자 이름
    private Long clientId; // 거래처 ID
    private String clientCode; // 거래처 코드
    private String clientName; // 거래처명
    private String clientAddress; // 고객 주소
    private String contactInfo; // 고객 연락처
    private String warehouseCode; // 창고 코드
    private LocalDate shipmentDate; // 출하 일자
    private Long shipmentNumber; // 출하 전표 번호
    private String comment; // 비고
    private List<ShipmentProductResponseDTO> products; // 출하 제품 리스트

    public static ShipmentResponseDTO mapToDto(Shipment shipment) {
        return new ShipmentResponseDTO(
                shipment.getId(),
                shipment.getWarehouse().getId(),
                shipment.getWarehouse().getName(),
                shipment.getEmployee().getId(),
                shipment.getEmployee().getEmployeeNumber(),
                shipment.getEmployee().getLastName() + shipment.getEmployee().getFirstName(),
                shipment.getClient().getId(),
                shipment.getClient().getCode(),
                shipment.getClientName(),
                shipment.getAddress(),
                shipment.getContactInfo(),
                shipment.getWarehouse().getCode(),
                shipment.getShipmentDate(),
                shipment.getShipmentNumber(),
                shipment.getComment(),
                shipment.getProducts().stream()
                        .map(ShipmentProductResponseDTO::mapToDto)
                        .collect(Collectors.toList())
        );
    }
}
