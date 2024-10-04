package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionListDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class QualityInspectionServiceImpl implements QualityInspectionService{


    @Override
    public List<QualityInspectionListDTO> findAllQualityInspection() {
        return List.of();
    }

    @Override
    public Optional<QualityInspectionDetailDTO> findQualityInspection(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<QualityInspectionDetailDTO> createQualityInspection(QualityInspectionDetailDTO dto) {
        return Optional.empty();
    }

    @Override
    public Optional<QualityInspectionDetailDTO> updateQualityInspection(Long id, QualityInspectionDetailDTO dto) {
        return Optional.empty();
    }

    @Override
    public void deleteQualityInspection(Long id) {

    }
}
