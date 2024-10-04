package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.DefectCategory;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.DefectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefectCategoryRepository extends JpaRepository<DefectCategory, Long> ,DefectCategoryRepositoryCustom{
}
