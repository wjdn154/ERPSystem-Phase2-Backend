package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseListDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WarehouseTransferRepositoryCustom {
    Long getNextTransferNumber(LocalDate transferDate);

    List<WarehouseTransferResponseListDTO> findTransfers(LocalDate startDate, LocalDate endDate);

    Optional<WarehouseTransferResponseDTO> findTransferDetailById(Long transferId);

    Long findMaxTransferNumberByDate(LocalDate transferDate);
}
