package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListMaintenanceHistoryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.MaintenanceHistoryDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.MaintenanceHistoryDetailShowDTO;

import java.util.List;
import java.util.Optional;

public interface MaintenanceHistoryService {

    //유지보수 이력 리스트 조회
    List<ListMaintenanceHistoryDTO> findAllMaintenanceHistory();

    //유지보수 이력 상세 조회
    Optional<MaintenanceHistoryDetailShowDTO> findMaintenanceHistoryById(Long id);

    //유지보수 이력 상세 삭제
    void deleteMaintenanceHistory(Long id);

    //유지보수 이력 상세 등록
    Optional<MaintenanceHistoryDetailShowDTO> saveMaintenanceHistory(MaintenanceHistoryDetailDTO dto);

    //유지보수 이력 상세 수정
    Optional<MaintenanceHistoryDetailShowDTO> updateMaintenanceHistory(Long id, MaintenanceHistoryDetailShowDTO dto);
}
