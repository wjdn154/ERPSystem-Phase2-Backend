package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListWorkerDTO;

import java.util.List;

public interface WorkerRepositoryCustom {

    List<ListWorkerDTO> findAllWorkerByDepartment();
}
