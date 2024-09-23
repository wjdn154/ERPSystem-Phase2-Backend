package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.HazardousMaterial;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.HazardousMaterial;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HazardousMaterialRepository extends JpaRepository<HazardousMaterial, Long>, HazardousMaterialRepositoryCustom {


    boolean existsByHazardousMaterialCode(String hazardousMaterialCode);

    Optional<HazardousMaterial> findByHazardousMaterialCode(String hazardousMaterialCode);

    void deleteByMaterialData(MaterialData materialData);

    boolean existsByHazardousMaterialCodeAndIdNot(String hazardousMaterialCode, Long id);
}
