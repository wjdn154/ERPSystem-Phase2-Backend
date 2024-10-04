package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.DefectType;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.DefectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefectTypeRepository extends JpaRepository<DefectType, Long> , DefectTypeRepositoryCustom{

    Optional<DefectType> findByCode(String defectTypeCode);
}
