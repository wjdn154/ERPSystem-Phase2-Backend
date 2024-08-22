package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;

import java.util.List;

public interface WorkcenterService {

    Workcenter updateWorkcenter(String code, WorkcenterDTO workcenterDTO);

    List<Workcenter> deleteByIds(List<Long> ids);

//    Optional<Workcenter> deleteById(Long id);

    Workcenter save(WorkcenterDTO workcenterDTO);
}

