package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.HazardousMaterial;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.HazardousMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HazardousMaterialRepository extends JpaRepository<HazardousMaterial, Long>, HazardousMaterialRepositoryCustom {

    List<HazardousMaterial> findAllByCompanyId(Long companyId);

    boolean existsHazardousMaterialCode(String hazardousMaterialCode);

    Optional<HazardousMaterial> findByHazardousMaterialCode(String hazardousMaterialCode);
}
