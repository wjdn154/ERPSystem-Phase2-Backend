package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.*;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection.QualityInspectionRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report.WorkPerformanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final ProductRepository productRepository;

    //모든 품질 검사 리스트 조회
    @Override
    public List<QualityInspectionListDTO> findAllQualityInspection() {
        return qualityInspectionRepository.findAllByOrderByIdDesc().stream()
                .map(qualityInspection -> {
                    // 총 수량
                    BigDecimal totalQuantity = qualityInspection.getWorkPerformance().getActualQuantity();

//                    // 불량 수량 계산: `isPassed`가 `false`인 `InspectedProduct`의 개수
//                    Long defectiveQuantity = qualityInspection.getInspectedProducts().stream()
//                            .filter(inspectedProduct -> !inspectedProduct.getIsPassed()) // 검사 불통과 제품
//                            .count();

                    // 불량 수량 계산: `isPassed`가 `false`인 `InspectedProduct`의 개수
                    BigDecimal defectiveQuantity = BigDecimal.valueOf(qualityInspection.getInspectedProducts().stream()
                            .filter(inspectedProduct -> !inspectedProduct.getIsPassed()) // 검사 불통과 제품
                            .count());


                    // 통과 수량 계산
                    BigDecimal passedQuantity = totalQuantity.subtract(defectiveQuantity);

                    // `QualityInspectionListDTO` 생성 및 반환
                    return new QualityInspectionListDTO(
                            qualityInspection.getId(),
                            qualityInspection.getInspectionCode(),
                            qualityInspection.getInspectionName(),
                            qualityInspection.getQualityInspectionType(),
                            qualityInspection.getWorkPerformance().getId(),
                            qualityInspection.getProduct().getCode(),
                            qualityInspection.getProduct().getName(),
                            totalQuantity,
                            defectiveQuantity,
                            passedQuantity
                    );
                })
                .collect(Collectors.toList());
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
    public Optional<QualityInspectionDetailDTO> createQualityInspection(QualityInspectionSaveDTO dto) {

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
    public Optional<QualityInspectionDetailDTO> updateQualityInspection(Long id, QualityInspectionUpdateDTO dto) {

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

    //품질검사 등록 dto를 엔티티로 변환
    private QualityInspection qualityInspectionToEntity(QualityInspectionSaveDTO dto) {

        WorkPerformance workPerformance = workPerformanceRepository.findById(dto.getWorkPerformanceId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작업실적 아이디를 조회할 수 없습니다."));

        Product product = productRepository.findByCode(dto.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 품목 코드를 조회할 수 없습니다. "));

        QualityInspection qualityInspection = QualityInspection.builder()
                .inspectionCode(dto.getInspectionCode())
                .inspectionName(dto.getInspectionName())
                .description(dto.getDescription())
                .qualityInspectionType(dto.getQualityInspectionType())
                .workPerformance(workPerformance)
                .product(product)
                .build();

        return qualityInspection;
    }

    //품질검사 수정 dto를 엔티티로 변환
    private QualityInspection updateQualityInspectionToEntity(QualityInspection qualityInspection, QualityInspectionUpdateDTO dto) {

        WorkPerformance workPerformance = workPerformanceRepository.findById(dto.getWorkPerformanceId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작업실적 아이디를 조회할 수 없습니다."));

        Product product = productRepository.findByCode(dto.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 품목 코드를 조회할 수 없습니다. "));

        qualityInspection.setInspectionCode(dto.getInspectionCode());
        qualityInspection.setInspectionName(dto.getInspectionName());
        qualityInspection.setDescription(dto.getDescription());
        qualityInspection.setQualityInspectionType(dto.getQualityInspectionType());
        qualityInspection.setWorkPerformance(workPerformance);
        qualityInspection.setProduct(product);

        return qualityInspection;
    }

    //엔티티를 QualityInspectionDetailDTO로 변환
    private QualityInspectionDetailDTO qualityInspectionToDTO(QualityInspection qualityInspection) {

        List<InspectedProductDTO> inspectedProducts = qualityInspection.getInspectedProducts().stream()
                .map(inspect -> new InspectedProductDTO(
                                inspect.getId(),
                                inspect.getIsPassed().toString(),
                                inspect.getDefectCategory().getName(),
                                inspect.getDefectCount()
                        )).collect(Collectors.toList());

        QualityInspectionDetailDTO qualityInspectionDetail = QualityInspectionDetailDTO.builder()
                .id(qualityInspection.getId())
                .inspectionCode(qualityInspection.getInspectionCode())
                .inspectionName(qualityInspection.getInspectionName())
                .qualityInspectionType(qualityInspection.getQualityInspectionType())
                .workPerformanceId(qualityInspection.getWorkPerformance().getId())
                .productCode(qualityInspection.getProduct().getCode())
                .productName(qualityInspection.getProduct().getName())
                .inspectedProducts(inspectedProducts)
                .build();

        return qualityInspectionDetail;
    }
}
