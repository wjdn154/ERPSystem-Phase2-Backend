package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.MaintenanceHistory;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListMaintenanceHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceHistoryRepository
        extends JpaRepository<MaintenanceHistory, Long>, MaintenanceHistoryRepositoryCustom {

        List<MaintenanceHistory> findAllByCompanyIdOrderByMaintenanceDateDesc(Long companyId);

}
