package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface QualityInspectionRepository extends JpaRepository<QualityInspection, Long> {

    List<QualityInspection> findAllByOrderByIdDesc();

    boolean existsByInspectionCode(String inspectionCode);
}
