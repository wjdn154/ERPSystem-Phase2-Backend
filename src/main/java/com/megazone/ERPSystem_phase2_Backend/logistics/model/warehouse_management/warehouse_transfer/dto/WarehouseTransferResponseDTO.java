package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferResponseDTO {
    private Long id;                         // 창고 이동 ID
    private String transferCode;
    private String sendingWarehouseName;
    private String receivingWarehouseName;
    private String employeeName;
    private LocalDateTime transferDate;
    private String status;
    private String comment;
    private List<WarehouseTransferProductResponseDTO> products;

    public WarehouseTransferResponseDTO(WarehouseTransfer transfer) {
        this.id = transfer.getId();
        this.transferCode = transfer.getTransferDate().toLocalDate() + " - " + transfer.getTransferNumber();
        this.sendingWarehouseName = transfer.getSendingWarehouse().getName();
        this.receivingWarehouseName = transfer.getReceivingWarehouse().getName();
        this.employeeName = transfer.getEmployee().getLastName() + "" + transfer.getEmployee().getFirstName();
        this.transferDate = transfer.getTransferDate();
        this.status = transfer.getStatus().name();
        this.comment = transfer.getComment();
        this.products = transfer.getProducts().stream()
                .map(WarehouseTransferProductResponseDTO::new)
                .collect(Collectors.toList());
    }
}
