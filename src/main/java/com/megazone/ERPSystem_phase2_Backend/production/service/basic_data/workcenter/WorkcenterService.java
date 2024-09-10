package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;

import java.util.List;
import java.util.Optional;

public interface WorkcenterService {

    Optional<WorkcenterDTO> updateByCode(Long company_id, String code, WorkcenterDTO workcenterDTO);

    Workcenter save(Long company_id, WorkcenterDTO workcenterDTO);

    List<WorkcenterDTO> findByNameContaining(Long company_id, String name);

    Optional<WorkcenterDTO> findByCode(Long company_id, String code);

    Optional<WorkcenterDTO> findById(Long company_id, Long id);

    Optional<WorkcenterDTO> deleteByCode(Long company_id, String code);

    List<WorkcenterDTO> findAll(Long company_id);

    List<WarehouseResponseDTO> findAllFactories(Long company_id);

    List<EquipmentDataDTO> findEquipmentByWorkcenterCode(Long company_id, String equipmentCode);

    List<WorkerAssignmentDTO> findWorkerAssignmentsByWorkcenterCode(Long company_id, String workcenterCode);
}
