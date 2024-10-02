package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.DefectCategoryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InboundRegistrationDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection.QualityInspectionRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report.WorkPerformanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class QualityInspectionServiceImpl implements QualityInspectionService{

    private final QualityInspectionRepository qualityInspectionRepository;
    private final WorkPerformanceRepository workPerformanceRepository;

    //모든 품질 검사 리스트 조회
    @Override
    public List<QualityInspectionListDTO> findAllQualityInspection() {

        return qualityInspectionRepository.findAllByOrderByIdDesc().stream()
                .map(qualityInspection -> new QualityInspectionListDTO(
                       qualityInspection.getId(),
                        qualityInspection.getInspectionCode(),
                        qualityInspection.getInspectionName(),
                        qualityInspection.getDescription(),
                        qualityInspection.getIsPassed(),
                        qualityInspection.getQualityInspectionType(),
                        qualityInspection.getWorkPerformance().getId(),
                        qualityInspection.getWorkPerformance().getName()
                )).collect(Collectors.toList());

    }

    //해당 품질 검사 상세 조회
    @Override
    public Optional<QualityInspectionDetailDTO> findQualityInspection(Long id) {

        //아이디 존재 여부 확인
        QualityInspection qualityInspection = qualityInspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 품질 검사 아이디를 조회할 수 없습니다."));

        QualityInspectionDetailDTO qualityInspectionDetailDTO = qualityInspectionToDTO(qualityInspection);

        return Optional.of(qualityInspectionDetailDTO);
    }


    //해당 품질 검사 상세 등록
    @Override
    public Optional<QualityInspectionDetailDTO> createQualityInspection(QualityInspectionDetailDTO dto) {

        //품질 검사 코드 중복 확인
        if(qualityInspectionRepository.existsByInspectionCode(dto.getInspectionCode())){
            throw new IllformedLocaleException("이미 존재하는 검사 코드 입니다.");
        }

        //dto를 엔티티로 변환
//        QualityInspection qualityInspection = qualityInspectionToEntity(dto);
//
//        //엔티티 저장
//        QualityInspection saveQualityInspection = qualityInspectionRepository.save(qualityInspection);
//
//        //엔티티를 dto로 변환
//        QualityInspectionDetailDTO qualityInspectionDetailDTO = qualityInspectionToDTO(saveQualityInspection);
//
//        return Optional.of(qualityInspectionDetailDTO);

        return null;
    }

    //해당 품질 검사 상세 수정
    @Override
    public Optional<QualityInspectionDetailDTO> updateQualityInspection(Long id, QualityInspectionDetailDTO dto) {

        //아이디 존재 여부 확인
        QualityInspection qualityInspection = qualityInspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 품질 검사 아이디를 조회할 수 없습니다."));

        //품질 검사 코드 중복 확인
        if(qualityInspectionRepository.existsByInspectionCode(dto.getInspectionCode())){
            throw new IllformedLocaleException("이미 존재하는 검사 코드 입니다.");
        }

        //dto를 엔티티로 변환
//        QualityInspection qualityInspectionEntity = updateQualityInspectionToEntity(id, dto);
//
//        //엔티티 수정
//        QualityInspection updateQualityInspection = qualityInspectionRepository.save(qualityInspectionEntity);
//
//        //엔티티를 dto로 변환
//        QualityInspectionDetailDTO updateQualityInspectionDetailDTO = qualityInspectionToDTO(updateQualityInspection);
//
//        return Optional.of(updateQualityInspectionDetailDTO);

        return null;
    }

    //해당 품질 검사 상세 삭제
    @Override
    public void deleteQualityInspection(Long id) {

        //아이디 존재 여부 확인
        QualityInspection qualityInspection = qualityInspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 품질 검사 아이디를 조회할 수 없습니다."));

        qualityInspectionRepository.delete(qualityInspection);
    }

    //엔티티를 QualityInspectionDetailDTO로 변환
    private QualityInspectionDetailDTO qualityInspectionToDTO(QualityInspection qualityInspection) {

        List<DefectCategoryDTO> defectCategoryList = qualityInspection.getDefectCategories().stream()
                .map(defectCategory -> DefectCategoryDTO.builder()
                        .id(defectCategory.getId())
                        .defectCategoryCode(defectCategory.getCode())
                        .defectCategoryName(defectCategory.getName())
                        .isUsed(defectCategory.getIsUsed().toString())
                        .build()
                ).toList();

        List<InboundRegistrationDTO> inboundRegistrationList = qualityInspection.getInboundRegistrations().stream()
                .map(inboundRegistration -> InboundRegistrationDTO.builder()
                        .id(inboundRegistration.getId())
                        .inboundCode(inboundRegistration.getCode())
                        .inboundTitle(inboundRegistration.getTitle())
                        .isDone(inboundRegistration.getIsDone().toString())
                        .build()
                ).toList();

        QualityInspectionDetailDTO qualityInspectionDetail = QualityInspectionDetailDTO.builder()
                .id(qualityInspection.getId())
                .inspectionCode(qualityInspection.getInspectionCode())
                .inspectionName(qualityInspection.getInspectionName())
                .description(qualityInspection.getDescription())
                .isPassed(qualityInspection.getIsPassed())
                .qualityInspectionType(qualityInspection.getQualityInspectionType())
                .workPerformanceId(qualityInspection.getWorkPerformance().getId())
                .workPerformanceName(qualityInspection.getWorkPerformance().getName())
                .defectCategory(defectCategoryList)
                .inboundRegistration(inboundRegistrationList).build();

        return qualityInspectionDetail;
    }
}
