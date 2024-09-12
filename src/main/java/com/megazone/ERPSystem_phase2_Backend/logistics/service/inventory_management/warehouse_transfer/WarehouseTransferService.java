package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseDTO;

import java.util.List;

public interface WarehouseTransferService {
    List<WarehouseTransferResponseDTO> getWarehouseTransfers(Long companyId);
}
