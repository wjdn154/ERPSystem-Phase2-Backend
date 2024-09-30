package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBomMaterial;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto.BomMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto.StandardBomDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom.StandardBomMaterialRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom.StandardBomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardBomServiceImpl implements StandardBomService {

    private final StandardBomRepository standardBomRepository;
    private final StandardBomMaterialRepository standardBomMaterialRepository;
    private final ProductRepository productRepository;
//    private final MaterialDataRepository materialDataRepository;


    @Override
    public StandardBomDTO createStandardBom(@Valid StandardBomDTO standardBomDTO) {

        if (standardBomDTO.getLossRate() < 0) {
            throw new IllegalArgumentException("손실율은 0 이상이어야 합니다.");
        }

        if (standardBomDTO.getStartDate().isAfter(standardBomDTO.getExpiredDate())) {
            throw new IllegalArgumentException("유효 시작일은 종료일 이전이어야 합니다.");
        }

        StandardBom savedBom = standardBomRepository.save(convertToEntity(standardBomDTO));

//        // 자재 레포지토리 필요
//        if (standardBomDTO.getBomMaterials() != null) {
//            List<StandardBomMaterial> bomMaterials = standardBomDTO.getBomMaterials().stream()
//                    .map(bomMaterialDTO -> {
//                        MaterialData materialData = materialDataRepository.findById(bomMaterialDTO.getMaterialId())
//                                .orElseThrow(() -> new EntityNotFoundException("자재를 찾을 수 없습니다."));
//
//                        return StandardBomMaterial.builder()
//                                .bom(savedBom)
//                                .material(materialData)
//                                .quantity(bomMaterialDTO.getQuantity())
//                                .lossRate(bomMaterialDTO.getLossRate())
//                                .build();
//                    }).toList();
//
//            standardBomMaterialRepository.saveAll(bomMaterials);
//            savedBom.setBomMaterials(bomMaterials);
//        }
//
//
        return convertToDTO(savedBom);
    }

    // 기본적으로 부모-자식 관계에서 false로 시작
    private StandardBomDTO convertToDTO(StandardBom standardBom) {
        return convertToDTO(standardBom, false);  // 부모가 아닌 경우
    }



    @Override
    public StandardBomDTO getStandardBomById(Long id) {
        StandardBom bom = standardBomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("요청하신 BOM을 찾을 수 없습니다."));

        List<StandardBomMaterial> materials = standardBomMaterialRepository.findByBomId(bom.getId());
        bom.setBomMaterials(materials);

        return convertToDTO(bom);
    }


    @Override
    public List<StandardBomDTO> getAllStandardBoms() {
        List<StandardBom> standardBoms = standardBomRepository.findAll();

        if (standardBoms.isEmpty()) {
            throw new EntityNotFoundException("등록된 BOM이 없습니다.");
        }

        return standardBoms.stream().map(this::convertToDTO).toList();
    }

    @Override
    public StandardBomDTO updateStandardBom(Long id, StandardBomDTO updatedBomDTO) {
        // 기존 BOM 조회
        StandardBom existingBom = standardBomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("요청하신 BOM을 찾을 수 없습니다."));

        if (updatedBomDTO.getLossRate() < 0) {
            throw new IllegalArgumentException("손실율은 0 이상이어야 합니다.");
        }

        if (updatedBomDTO.getStartDate().isAfter(updatedBomDTO.getExpiredDate())) {
            throw new IllegalArgumentException("유효 시작일은 종료일 이전이어야 합니다.");
        }

        // 상위 품목 처리: 상위 품목 없으면 null, 예외 처리 없이 정상 수정 가능
        Product parentProduct = productRepository.findById(updatedBomDTO.getParentProductId()).orElse(null);

        StandardBom newBomVersion = existingBom.toBuilder()
                .version(existingBom.getVersion() + 0.1)
                .createdDate(LocalDateTime.now())
                .remarks(updatedBomDTO.getRemarks())
                .lossRate(updatedBomDTO.getLossRate())
                .outsourcingType(updatedBomDTO.getOutsourcingType())
                .startDate(updatedBomDTO.getStartDate())
                .expiredDate(updatedBomDTO.getExpiredDate())
                .isActive(updatedBomDTO.getIsActive())
                .parentProduct(parentProduct)
                .build();

//        // 상위품목 반드시 필요?
//        Product parentProduct = productRepository.findById(updatedBomDTO.getParentProductId()).orElseThrow(() -> new EntityNotFoundException("상위 품목을 찾을 수 없습니다."));
//        newBomVersion.setParentProduct(parentProduct);

        // 자재목록
//        List<StandardBomMaterial> bomMaterials = Optional.ofNullable(standardBomDTO.getBomMaterials())
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(materialDTO -> {
//                    MaterialData materialData = materialDataRepository.findById(materialDTO.getMaterialId())
//                            .orElseThrow(() -> new EntityNotFoundException("자재를 찾을 수 없습니다."));
//                    return StandardBomMaterial.builder()
//                            .bom(newBomVersion)
//                            .material(materialData)
//                            .quantity(materialDTO.getQuantity())
//                            .lossRate(materialDTO.getLossRate())
//                            .build();
//                })
//                .collect(Collectors.toList());
//        newBomVersion.setBomMaterials(bomMaterials);

        StandardBom savedBom = standardBomRepository.save(newBomVersion);
        return convertToDTO(savedBom);
    }

    @Override
    public StandardBomDTO deleteStandardBom(Long id) {
        StandardBom bom = standardBomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("요청하신 BOM을 찾을 수 없습니다."));

        standardBomMaterialRepository.deleteByBomId(bom.getId());
        standardBomRepository.delete(bom);

        return convertToDTO(bom);
    }

    // 정전개를 위한 상위 품목으로 하위 BOM 구성 조회
    @Override
    public List<StandardBomDTO> getChildBoms(Long parentProductId, Set<Long> checkedBomIds) {

        if (checkedBomIds.contains(parentProductId)) {
            throw new IllegalArgumentException("순환 참조 발생");
        }

        checkedBomIds.add(parentProductId);

        List<StandardBom> childBoms = standardBomRepository.findByParentProductId(parentProductId);

        if (childBoms.isEmpty()) {
            throw new EntityNotFoundException("해당 상위 품목에 등록된 하위 BOM이 없습니다.");
        }

        List<StandardBomDTO> childBomDTOs = childBoms.stream().map(this::convertToDTO).toList();

        for (StandardBomDTO childBomDTO : childBomDTOs)
            childBomDTO.setChildBoms(getChildBoms(childBomDTO.getId(), checkedBomIds));

        return childBomDTOs;
    }



//
//    public List<StandardBomDTO> getChildBoms(Long parentProductId) {
//
//        if (!productRepository.existsById(parentProductId)) {
//            throw new EntityNotFoundException("해당 상위 품목을 찾을 수 없습니다.");
//        }
//
//        List<StandardBom> childBoms = standardBomRepository.findByParentProductId(parentProductId);
//
//        return childBoms.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

    // 단일 상위 BOM
    @Override
    public StandardBomDTO getParentBom(Long id) {

        StandardBomDTO currentBom = getStandardBomById(id);

        StandardBomDTO parentBom = currentBom.getParentBom();
        if (parentBom == null) {
            throw new EntityNotFoundException("상위 BOM이 존재하지 않습니다.");
        }
        return parentBom;
    }

//    // 역전개를 위한 하위 품목으로 상위 BOM 구성 조회 ( 여러 개일 경우 )
//    public List<StandardBomDTO> getParentBoms(Long childProductId) {
//        List<StandardBom> parentBoms = standardBomRepository.findByChildProductId(childProductId);
//
//        if (parentBoms.isEmpty())
//            throw new EntityNotFoundException("해당 하위 품목에 등록된 상위 품목이 없습니다.");
//
//        return parentBoms.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//
//    }

    private StandardBom convertToEntity(StandardBomDTO standardBomDTO) {
        
        StandardBom parentBom = Optional.ofNullable(standardBomDTO.getParentBom()).map(this::convertToEntity).orElse(null);

        List<StandardBom> childBoms = Optional.ofNullable(standardBomDTO.getChildBoms()).orElse(Collections.emptyList()).stream().map(this::convertToEntity).toList();

//        // 자재 목록 설정
//        List<StandardBomMaterial> bomMaterials = Optional.ofNullable(standardBomDTO.getBomMaterials())
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(materialDTO -> {
//                    MaterialData materialData = materialDataRepository.findById(materialDTO.getMaterialId())
//                            .orElseThrow(() -> new EntityNotFoundException("자재를 찾을 수 없습니다."));
//                    return StandardBomMaterial.builder()
//                            .material(materialData)
//                            .quantity(materialDTO.getQuantity())
//                            .lossRate(materialDTO.getLossRate())
//                            .build();
//                })
//                .collect(Collectors.toList());

        // StandardBom 엔티티 생성
        return StandardBom.builder()
                .id(standardBomDTO.getId())
                .version(standardBomDTO.getVersion())
                .createdDate(LocalDateTime.now())
                .remarks(standardBomDTO.getRemarks())
                .lossRate(standardBomDTO.getLossRate())
                .outsourcingType(standardBomDTO.getOutsourcingType())
                .startDate(standardBomDTO.getStartDate())
                .expiredDate(standardBomDTO.getExpiredDate())
                .isActive(standardBomDTO.getIsActive())
//                .bomMaterials(bomMaterials)
                .childBoms(childBoms)
                .parentBom(parentBom)
                .build();
    }

    // 부모와 자식 관계를 관리하는 메서드
    private StandardBomDTO convertToDTO(StandardBom standardBom, boolean isParent) {
        List<BomMaterialDTO> materialDTOs = Optional.ofNullable(standardBom.getBomMaterials())
                .orElse(Collections.emptyList())
                .stream()
                .map(material -> BomMaterialDTO.builder()
                        .id(material.getId())
                        .materialId(material.getMaterial().getId())
                        .quantity(material.getQuantity())
                        .lossRate(material.getLossRate())
                        .build())
                .toList();

        StandardBomDTO parentBomDTO = Optional.ofNullable(standardBom.getParentBom())
                .map(bom -> convertToDTO(bom, true)) // 부모 BOM을 처리할 때는 자식 호출을 막음
                .orElse(null);

        List<StandardBomDTO> childBomDTOs = Collections.emptyList();
//        List<StandardBomDTO> childBomDTOs = Optional.ofNullable(standardBom.getChildBoms()).orElse(Collections.emptyList()).stream().map(this::convertToDTO).toList();

        // 부모 객체가 아닌 경우에만 자식 BOM을 처리
        if (!isParent) {
            childBomDTOs = Optional.ofNullable(standardBom.getChildBoms())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(bom -> convertToDTO(bom, false))
                    .toList();
        }

        return StandardBomDTO.builder()
                .id(standardBom.getId())
                .version(standardBom.getVersion())
                .createdDate(standardBom.getCreatedDate())
                .remarks(standardBom.getRemarks())
                .lossRate(standardBom.getLossRate())
                .outsourcingType(standardBom.getOutsourcingType())
                .startDate(standardBom.getStartDate())
                .expiredDate(standardBom.getExpiredDate())
                .isActive(standardBom.getIsActive())
                // parentProduct가 null일 수 있으므로 안전하게 처리
                .parentProductId(Optional.ofNullable(standardBom.getParentProduct()).map(Product::getId).orElse(null))
//                .bomMaterials(materialDTOs)
                .parentBom(parentBomDTO)
                .childBoms(childBomDTOs)
                .build();
    }


}
