package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseListDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WarehouseTransferService {

    WarehouseTransferResponseDTO createWarehouseTransfer(WarehouseTransferRequestDTO requestDTO);

    List<WarehouseTransferResponseListDTO> findTransfers(LocalDate startDate, LocalDate endDate);

    Optional<WarehouseTransferResponseDTO> getTransferDetail(Long transferId);

    WarehouseTransferResponseDTO updateTransfer(Long transferId, WarehouseTransferRequestDTO updateDTO);

    void deleteTransfer(Long transferId);

}
