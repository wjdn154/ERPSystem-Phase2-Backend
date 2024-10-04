package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentUpdateRequestDTO {
    private Long id;
    private Long warehouseId; // 창고 ID
    private Long employeeId; // 담당자 ID
    private Long clientId; // 고객 ID
    private Long contactInfoId; // 연락처 ID
    private Long warehouseAddressId; // 창고 주소 ID
    private LocalDate shipmentDate; // 출하 일자
    private String comment; // 비고
    private List<ShipmentProductRequestDTO> products; // 출하 제품 리스트
}
