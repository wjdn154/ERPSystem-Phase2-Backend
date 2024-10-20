package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.DefectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefectCategoryRepository extends JpaRepository<DefectCategory, Long>, DefectCategoryRepositoryCustom{

    Optional<DefectCategory> findByCode(String defectCategoryCode);
}
