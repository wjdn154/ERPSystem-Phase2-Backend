package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;

import java.util.List;
import java.util.Optional;

public interface WorkcenterService {

    Optional<Workcenter> updateWorkcenter(String code, WorkcenterDTO workcenterDTO);

    List<Workcenter> deleteByIds(List<Long> ids);

    Optional<Workcenter> deleteById(Long id);

    Workcenter save(WorkcenterDTO workcenterDTO);

    List<WorkcenterDTO> findByNameContaining(String name);

    Optional<WorkcenterDTO> findByName(String name);

    Optional<WorkcenterDTO> findByCode(String code);

    Optional<WorkcenterDTO> findById(Long id);

    List<WorkcenterDTO> findAll();
}
