package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBomMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardBomMaterialRepository extends JpaRepository<StandardBomMaterial, Long> {
}
