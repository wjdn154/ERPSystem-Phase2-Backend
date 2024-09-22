package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;

import java.util.List;
import java.util.Optional;

public interface StandardBomService {
    StandardBom createStandardBom(StandardBom standardBom);

    Optional<StandardBom> getStandardBomById(Long id);

    List<StandardBom> getAllStandardBoms();

    StandardBom updateStandardBom(Long id, StandardBom standardBom);

    void deleteStandardBom(Long id);

    List<StandardBom> getChildBoms(Long parentProductId);

    List<StandardBom> getParentBoms(Long childProductId);

}
