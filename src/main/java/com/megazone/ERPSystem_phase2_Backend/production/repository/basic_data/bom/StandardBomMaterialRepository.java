package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBomMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StandardBomMaterialRepository extends JpaRepository<StandardBomMaterial, Long> {
    List<StandardBomMaterial> findByBomId(Long id);

    void deleteByBomId(Long id);

}
