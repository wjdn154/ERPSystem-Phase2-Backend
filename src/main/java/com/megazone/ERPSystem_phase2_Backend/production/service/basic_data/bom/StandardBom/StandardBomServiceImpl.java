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



    @Override
    public Optional<StandardBomDTO> getStandardBomById(Long id) {
        return standardBomRepository.findById(id)
                .map(bom -> {
                    List<StandardBomMaterial> materials = standardBomMaterialRepository.findByBomId(bom.getId());
                    bom.setBomMaterials(materials);
                    return convertToDTO(bom);
                });
    }


    @Override
    public List<StandardBomDTO> getAllStandardBoms() {
        List<StandardBom> standardBoms = standardBomRepository.findAll();

        return standardBoms.stream().map(this::convertToDTO).toList();
    }

    @Override
    public StandardBomDTO updateStandardBom(Long id, StandardBomDTO updatedBomDTO) {
        // 기존 BOM 조회
        StandardBom existingBom = standardBomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BOM을 찾을 수 없습니다."));

        StandardBom newBomVersion = existingBom.toBuilder()
                .version(existingBom.getVersion() + 0.1)
                .createdDate(LocalDateTime.now())
                .remarks(updatedBomDTO.getRemarks())
                .lossRate(updatedBomDTO.getLossRate())
                .outsourcingType(updatedBomDTO.getOutsourcingType())
                .startDate(updatedBomDTO.getStartDate())
                .expiredDate(updatedBomDTO.getExpiredDate())
                .isActive(updatedBomDTO.getIsActive())
                .build();

        // 상위품목
        Product parentProduct = productRepository.findById(updatedBomDTO.getParentProductId()).orElseThrow(() -> new EntityNotFoundException("상위 품목을 찾을 수 없습니다."));
        newBomVersion.setParentProduct(parentProduct);

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
        return convertToDTO(savedBom); // 새 버전 저장
    }



    @Override
    public void deleteStandardBom(Long id) {
        StandardBom bom = standardBomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BOM을 찾을 수 없습니다."));

        // 자재 목록 삭제
        standardBomMaterialRepository.deleteByBomId(bom.getId());

        // BOM 삭제
        standardBomRepository.delete(bom);
    }


    @Override
    public List<StandardBomDTO> getChildBoms(Long parentProductId) {
        List<StandardBom> childBoms = standardBomRepository.findByParentProductId(parentProductId);

        return childBoms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StandardBomDTO> getParentBoms(Long childProductId) {
        List<StandardBom> parentBoms = standardBomRepository.findByChildProductId(childProductId);

        return parentBoms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    private StandardBom convertToEntity(StandardBomDTO standardBomDTO) {
        // 상위 제품 조회
        Product parentProduct = productRepository.findById(standardBomDTO.getParentProductId())
                .orElseThrow(() -> new EntityNotFoundException("상위 제품을 찾을 수 없습니다."));

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
                .parentProduct(parentProduct)
//                .bomMaterials(bomMaterials)
                .build();
    }

    private StandardBomDTO convertToDTO(StandardBom standardBom) {
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
                .parentProductCode(standardBom.getParentProduct().getCode())
//                .bomMaterials(materialDTOs)
                .build();
    }


}
