package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto.StandardBomDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StandardBomService {

    StandardBomDTO createStandardBom(@Valid StandardBomDTO standardBomDTO);

    StandardBomDTO getStandardBomById(Long id);

    List<StandardBomDTO> getAllStandardBoms();

    StandardBomDTO updateStandardBom(Long id, StandardBomDTO standardBomDTO);

    StandardBomDTO deleteStandardBom(Long id);

    //    List<StandardBomDTO> getChildBoms(Long parentProductId);
    List<StandardBomDTO> getChildBoms(Long parentProductId, Set<Long> checkedBomIds);
//    List<StandardBomDTO> getParentBoms(Long childProductId);

    StandardBomDTO getParentBom(Long childProductId);
}
