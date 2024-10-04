package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryAdjustment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryAdjustmentResponseDTO {
    private Long adjustmentId;
    private Long inspectionId;
    private String employeeName;
    private LocalDate adjustmentDate;
    private List<InventoryAdjustmentDetailDTO> adjustmentDetails;

    public static InventoryAdjustmentResponseDTO mapToDTO(InventoryAdjustment adjustment) {
        List<InventoryAdjustmentDetailDTO> detailDTOList = adjustment.getAdjustmentDetails()
                .stream()
                .map(InventoryAdjustmentDetailDTO::mapToDTO)
                .collect(Collectors.toList());

        return new InventoryAdjustmentResponseDTO(
                adjustment.getId(),
                adjustment.getInventoryInspection().getId(),
                adjustment.getEmployee().getFirstName() + " " + adjustment.getEmployee().getLastName(),
                adjustment.getAdjustmentDate(),
                detailDTOList
        );
    }
}
