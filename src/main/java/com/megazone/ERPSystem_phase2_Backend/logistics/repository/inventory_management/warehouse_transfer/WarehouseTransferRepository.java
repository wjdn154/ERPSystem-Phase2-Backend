package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseTransferRepository extends JpaRepository<WarehouseTransfer, Long>, WarehouseTransferRepositoryCustom {
    List<WarehouseTransfer> findAllByCompanyId(Long companyId);
}
