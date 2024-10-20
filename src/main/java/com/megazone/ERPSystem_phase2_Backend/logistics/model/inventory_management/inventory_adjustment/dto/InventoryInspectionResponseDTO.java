package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspection;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.enums.InspectionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryInspectionResponseDTO {
    private Long id; // 실사 ID
    private String inspectionDate; // 실사 일자
    private Long inspectionNumber; // 실사 번호
    private Long employeeId; // 담당자 ID
    private String employeeName; // 담당자 이름
    private Long warehouseId; // 창고 ID
    private String warehouseName; // 창고 이름
    private InspectionStatus status; // 실사 상태
    private String comment; // 코멘트 (비고)
    private List<InventoryInspectionDetailResponseDTO> details; // 실사 품목 리스트

    public static InventoryInspectionResponseDTO mapToDto(InventoryInspection inventoryInspection) {
        return new InventoryInspectionResponseDTO(
                inventoryInspection.getId(),
                inventoryInspection.getInspectionDate().toString(),
                inventoryInspection.getInspectionNumber(),
                inventoryInspection.getEmployee().getId(),
                inventoryInspection.getEmployee().getLastName() + inventoryInspection.getEmployee().getFirstName(),
                inventoryInspection.getWarehouse().getId(),
                inventoryInspection.getWarehouse().getName(),
                inventoryInspection.getStatus(),
                inventoryInspection.getComment(),
                inventoryInspection.getDetails().stream()
                        .map(InventoryInspectionDetailResponseDTO::mapToDto)
                        .collect(Collectors.toList())
        );
    }
}
