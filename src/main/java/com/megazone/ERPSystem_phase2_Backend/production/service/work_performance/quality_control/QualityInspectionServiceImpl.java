package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.InboundRegistration;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.DefectCategory;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.DefectType;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.DefectCategoryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InboundRegistrationDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.goods_receipt.InboundRegistration.InboundRegistrationRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.DefectCategory.DefectCategoryRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.DefectType.DefectTypeRepository;
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
    private final DefectTypeRepository defectTypeRepository;

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
        QualityInspection qualityInspection = qualityInspectionToEntity(dto);

        //엔티티 저장
        QualityInspection saveQualityInspection = qualityInspectionRepository.save(qualityInspection);

        //엔티티를 dto로 변환
        QualityInspectionDetailDTO qualityInspectionDetailDTO = qualityInspectionToDTO(saveQualityInspection);

        return Optional.of(qualityInspectionDetailDTO);

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
        QualityInspection qualityInspectionEntity = updateQualityInspectionToEntity(qualityInspection, dto);

        //엔티티 수정
        QualityInspection updateQualityInspection = qualityInspectionRepository.save(qualityInspectionEntity);

        //엔티티를 dto로 변환
        QualityInspectionDetailDTO updateQualityInspectionDetailDTO = qualityInspectionToDTO(updateQualityInspection);

        return Optional.of(updateQualityInspectionDetailDTO);

    }

    //해당 품질 검사 상세 삭제
    @Override
    public void deleteQualityInspection(Long id) {

        //아이디 존재 여부 확인
        QualityInspection qualityInspection = qualityInspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 품질 검사 아이디를 조회할 수 없습니다."));

        qualityInspectionRepository.delete(qualityInspection);
    }

    private QualityInspection qualityInspectionToEntity(QualityInspectionDetailDTO dto) {

        WorkPerformance workPerformance = workPerformanceRepository.findById(dto.getWorkPerformanceId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작업실적 아이디를 조회할 수 없습니다."));

        QualityInspection qualityInspection = QualityInspection.builder()
                .id(dto.getId())
                .inspectionCode(dto.getInspectionCode())
                .inspectionName(dto.getInspectionName())
                .description(dto.getDescription())
                .isPassed(Boolean.parseBoolean(dto.getIsPassed()))
                .qualityInspectionType(dto.getQualityInspectionType())
                .workPerformance(workPerformance)
                .defectCategories(
                        dto.getDefectCategory().stream()
                                .map(this::defectCategoryDtoToEntity)
                                .collect(Collectors.toList())
                )
                .inboundRegistrations(
                        dto.getInboundRegistration().stream()
                                .map(this::inboundRegistrationDtoToEntity)
                                .collect(Collectors.toList())
                )
                .build();

        return qualityInspection;
    }

    private InboundRegistration inboundRegistrationDtoToEntity(InboundRegistrationDTO dto) {

        return InboundRegistration.builder()
                .id(dto.getId())
                .code(dto.getInboundCode())
                .title(dto.getInboundTitle())
                .isDone(Boolean.parseBoolean(dto.getIsDone()))
                .build();
    }

    private DefectCategory defectCategoryDtoToEntity(DefectCategoryDTO dto) {

        DefectType defectType = defectTypeRepository.findByCode(dto.getDefectTypeCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 불량유형 코드를 조회할 수 없습니다."));

        return DefectCategory.builder()
                .id(dto.getId())
                .code(dto.getDefectCategoryCode())
                .name(dto.getDefectCategoryName())
                .isUsed(Boolean.parseBoolean(dto.getIsUsed()))
                .defectType(defectType)
                .build();
    }

    private QualityInspection updateQualityInspectionToEntity(QualityInspection qualityInspection, QualityInspectionDetailDTO dto) {

        WorkPerformance workPerformance = workPerformanceRepository.findById(dto.getWorkPerformanceId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작업실적 아이디를 조회할 수 없습니다."));

        qualityInspection.setInspectionCode(dto.getInspectionCode());
        qualityInspection.setInspectionName(dto.getInspectionName());
        qualityInspection.setDescription(dto.getDescription());
        qualityInspection.setIsPassed(Boolean.parseBoolean(dto.getIsPassed()));
        qualityInspection.setQualityInspectionType(dto.getQualityInspectionType());
        qualityInspection.setWorkPerformance(workPerformance);

        qualityInspection.setDefectCategories(
                dto.getDefectCategory().stream()
                        .map(this::defectCategoryDtoToEntity)
                        .collect(Collectors.toList()));

        qualityInspection.setInboundRegistrations(
                dto.getInboundRegistration().stream()
                        .map(this::inboundRegistrationDtoToEntity)
                        .collect(Collectors.toList()));

        return qualityInspection;
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
                .isPassed(qualityInspection.getIsPassed().toString())
                .qualityInspectionType(qualityInspection.getQualityInspectionType())
                .workPerformanceId(qualityInspection.getWorkPerformance().getId())
                .workPerformanceName(qualityInspection.getWorkPerformance().getName())
                .defectCategory(defectCategoryList)
                .inboundRegistration(inboundRegistrationList)
                .build();

        return qualityInspectionDetail;
    }
}
