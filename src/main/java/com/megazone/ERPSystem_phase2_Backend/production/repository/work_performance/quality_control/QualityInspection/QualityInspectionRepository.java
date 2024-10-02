package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionListDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QualityInspectionRepository extends JpaRepository<QualityInspection, Long> ,QualityInspectionRepositoryCustom{

    List<QualityInspection> findAllByOrderByIdDesc();

    boolean existsByInspectionCode(String inspectionCode);
}
