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
    private Long id;
    private String inspectionDate;
    private Long inspectionNumber;
    private String employeeName;
    private String warehouseName;
    private InspectionStatus status;
    private String comment;
    private List<InventoryInspectionDetailResponseDTO> details;

    public static InventoryInspectionResponseDTO mapToDto(InventoryInspection inventoryInspection) {
        return new InventoryInspectionResponseDTO(
                inventoryInspection.getId(),
                inventoryInspection.getInspectionDate().toString(),
                inventoryInspection.getInspectionNumber(),
                inventoryInspection.getEmployee().getLastName() + inventoryInspection.getEmployee().getFirstName(),
                inventoryInspection.getWarehouse().getName(),
                inventoryInspection.getStatus(),
                inventoryInspection.getComment(),
                inventoryInspection.getDetails().stream()
                        .map(InventoryInspectionDetailResponseDTO::mapToDto)
                        .collect(Collectors.toList())
        );
    }
}
