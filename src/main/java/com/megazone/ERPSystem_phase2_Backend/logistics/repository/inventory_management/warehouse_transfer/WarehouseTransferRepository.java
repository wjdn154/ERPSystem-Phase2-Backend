package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseTransferRepository extends JpaRepository<WarehouseTransfer, Long>, WarehouseTransferRepositoryCustom {
}
