package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionListDTO;

import java.util.List;
import java.util.Optional;

public interface QualityInspectionService {
    
    //품질검사 리스트 조회
    List<QualityInspectionListDTO> findAllQualityInspection();

    //품질검사 상세 조회
    Optional<QualityInspectionDetailDTO> findQualityInspection(Long id);

    //품질검사 상세 등록
    Optional<QualityInspectionDetailDTO> createQualityInspection(QualityInspectionDetailDTO dto);

    //품질검사 상세 수정
    Optional<QualityInspectionDetailDTO> updateQualityInspection(Long id, QualityInspectionDetailDTO dto);

    //품질검사 삭제
    void deleteQualityInspection(Long id);
}
