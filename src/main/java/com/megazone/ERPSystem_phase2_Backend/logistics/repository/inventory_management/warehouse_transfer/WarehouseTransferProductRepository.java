package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransfer;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransferProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseTransferProductRepository extends JpaRepository<WarehouseTransferProduct, Long> {

    void deleteAllByWarehouseTransfer(WarehouseTransfer existingTransfer);

}
