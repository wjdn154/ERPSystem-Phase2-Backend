package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product.ProductService;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.DefectCategory;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.InspectedProduct;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductSaveDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection.DefectCategoryRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection.InspectedProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.quality_control.QualityInspection.QualityInspectionRepository;
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
public class InspectedProductServiceImpl implements InspectedProductService{

    private final InspectedProductRepository inspectedProductRepository;
    private final QualityInspectionService qualityInspectionService;
    private final ProductService productService;
    private final QualityInspectionRepository qualityInspectionRepository;
    private final DefectCategoryRepository defectCategoryRepository;

    //리스트 조회
    @Override
    public List<InspectedProductListDTO> findAllInspectedProduct() {

        return inspectedProductRepository.findAll().stream()
                .map(inspectedProduct -> InspectedProductListDTO.builder()
                        .id(inspectedProduct.getId())
                        .inspectionName(inspectedProduct.getQualityInspection().getInspectionName())
                        .productName(inspectedProduct.getQualityInspection().getProduct().getName())
                        .isPassed(inspectedProduct.getIsPassed().toString())
                        .defectCategoryName(
                                inspectedProduct.getDefectCategory() != null?
                                inspectedProduct.getDefectCategory().getName() :
                                "불량군 없음")
                        .defectCount(inspectedProduct.getDefectCount())
                        .build())
                .collect(Collectors.toList());
    }

    //상세 조회
    @Override
    public Optional<InspectedProductDetailDTO> findInspectedProduct(Long id) {

        //아이디 존재 여부 확인
        InspectedProduct inspectedProduct = inspectedProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 검사 품목 아이디를 조회할 수 없습니다."));

        InspectedProductDetailDTO inspectedProductDetailDTO = inspectedProductToDTO(inspectedProduct);

        return Optional.of(inspectedProductDetailDTO);
    }

    //상세 등록
    @Override
    public Optional<InspectedProductDetailDTO> createInspectedProduct(InspectedProductSaveDTO dto) {

        //품질검사 코드 존재여부 확인
        if(!qualityInspectionRepository.existsByInspectionCode(dto.getQualityInspectionCode())){
            throw new IllformedLocaleException("존재하지 않은 품질검사 코드 입니다.");
        }

        //dto를 엔티티로 변환
        InspectedProduct inspectedProduct = inspectedProductToEntity(dto);

        //엔티티 저장
        InspectedProduct saveInspectedProduct = inspectedProductRepository.save(inspectedProduct);

        //엔티티를 dto로 변환
        InspectedProductDetailDTO inspectedProductDetailDTO = inspectedProductToDTO(saveInspectedProduct);

        return Optional.of(inspectedProductDetailDTO);
    }

    //상세 수정
    @Override
    public Optional<InspectedProductDetailDTO> updateInspectedProduct(Long id, InspectedProductSaveDTO dto) {

        //아이디 존재 여부 확인
        InspectedProduct inspectedProduct = inspectedProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 검사품목 아이디를 조회할 수 없습니다."));

        //불량군 코드 확인 및 조회
        DefectCategory defectCategory = null;
        if(dto.getQualityInspectionCode() != null && !dto.getQualityInspectionCode().isEmpty()) {
            defectCategory = defectCategoryRepository.findByCode(dto.getDefectCategoryCode())
                    .orElseThrow(() -> new IllegalArgumentException("해당 불량군 코드를 조회할 수 없습니다."));
        }
        inspectedProduct.setIsPassed(Boolean.parseBoolean(dto.getIsPassed()));
        inspectedProduct.setDefectCategory(defectCategory);
        inspectedProduct.setDefectCount(dto.getDefectCount());

        InspectedProductDetailDTO inspectedProductDetailDTO = inspectedProductToDTO(inspectedProduct);

        return Optional.of(inspectedProductDetailDTO);
    }

    //삭제
    @Override
    public void deleteInspectedProduct(Long id) {

        //아이디 존재 여부 확인
        InspectedProduct inspectedProduct = inspectedProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 검사품목 아이디를 조회할 수 없습니다."));

        inspectedProductRepository.delete(inspectedProduct);
    }

    private InspectedProductDetailDTO inspectedProductToDTO(InspectedProduct inspectedProduct) {
        return null;
    }


    private InspectedProduct inspectedProductToEntity(InspectedProductSaveDTO dto) {
        return null;
    }
}
