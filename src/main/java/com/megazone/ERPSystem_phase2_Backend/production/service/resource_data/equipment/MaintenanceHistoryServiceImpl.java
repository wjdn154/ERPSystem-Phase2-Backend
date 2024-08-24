package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListMaintenanceHistoryDTO;

import java.util.List;

public class MaintenanceHistoryServiceImpl implements MaintenanceHistoryService{

    //유지보수 이력 리스트 조회
    @Override
    public List<ListMaintenanceHistoryDTO> findAllMaintenanceHistory() {
        return List.of();
    }
}
