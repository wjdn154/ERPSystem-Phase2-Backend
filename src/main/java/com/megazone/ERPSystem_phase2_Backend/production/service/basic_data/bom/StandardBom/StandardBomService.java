package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto.StandardBomDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface StandardBomService {

    StandardBomDTO createStandardBom(@Valid StandardBomDTO standardBomDTO);

    Optional<StandardBomDTO> getStandardBomById(Long id);

    List<StandardBomDTO> getAllStandardBoms();

    StandardBomDTO updateStandardBom(Long id, StandardBomDTO standardBomDTO);

    void deleteStandardBom(Long id);

    List<StandardBomDTO> getChildBoms(Long parentProductId);

    List<StandardBomDTO> getParentBoms(Long childProductId);
}
