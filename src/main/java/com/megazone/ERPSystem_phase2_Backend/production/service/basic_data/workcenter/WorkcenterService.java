package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;

import java.util.List;
import java.util.Optional;

public interface WorkcenterService {

    Optional<WorkcenterDTO> updateByCode(String code, WorkcenterDTO workcenterDTO);

    Workcenter save(WorkcenterDTO workcenterDTO);

//    List<WorkcenterDTO> findByNameContaining(String name);

    Optional<WorkcenterDTO> findByCode(String code);

    Optional<WorkcenterDTO> findById(Long id);

    Optional<WorkcenterDTO> deleteByCode(String code);

    List<WorkcenterDTO> findAll();

    List<WarehouseResponseDTO> findAllFactories();

    List<EquipmentDataDTO> findEquipmentByWorkcenterCode(String workcenterCode);

//    List<WorkerAssignmentDTO> findWorkerAssignmentsByWorkcenterCode(String workcenterCode);

    List<WorkerAssignmentDTO> findTodayWorkers(String workcenterCode);
}
