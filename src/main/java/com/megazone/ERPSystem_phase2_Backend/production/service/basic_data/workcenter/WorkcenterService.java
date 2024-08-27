package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;

import java.util.List;
import java.util.Optional;

public interface WorkcenterService {

    Optional<WorkcenterDTO> updateByCode(String code, WorkcenterDTO workcenterDTO);

    List<Workcenter> deleteByIds(List<Long> ids);

    Workcenter save(WorkcenterDTO workcenterDTO);

    List<WorkcenterDTO> findByNameContaining(String name);

    Optional<WorkcenterDTO> findByName(String name);

    Optional<WorkcenterDTO> findByCode(String code);

    Optional<WorkcenterDTO> findById(Long id);

    Optional<WorkcenterDTO> deleteByCode(String code);

    List<WorkcenterDTO> findAll();
}
