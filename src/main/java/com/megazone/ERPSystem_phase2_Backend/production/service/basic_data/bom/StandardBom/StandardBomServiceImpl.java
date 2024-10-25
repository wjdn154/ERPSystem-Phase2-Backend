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
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialData.MaterialDataRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardBomServiceImpl implements StandardBomService {

    private final StandardBomRepository standardBomRepository;
    private final StandardBomMaterialRepository standardBomMaterialRepository;
    private final ProductRepository productRepository;
    private final MaterialDataRepository materialDataRepository;


    // 최상위 BOM만 조회
    public List<StandardBomDTO> getTopLevelBoms() {
        List<StandardBom> topLevelBoms = standardBomRepository.findByParentBomIsNull();

        if (topLevelBoms.isEmpty()) {
            throw new EntityNotFoundException("등록된 최상위 BOM이 없습니다.");
        }

        return topLevelBoms.stream().map(this::convertToDTO).toList();
    }

    // 레벨별 조회
    public List<StandardBomDTO> getBomsByLevel(Long level) {
        List<StandardBom> boms = standardBomRepository.findByLevel(level);
        return boms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 품목 코드로 BOM 조회
    public List<StandardBomDTO> getBomsByProductId(Long productId) {
        List<StandardBom> boms = standardBomRepository.findByProductId(productId);
        return boms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

    public Map<String, BigDecimal> getTotalMaterials(Long bomId) {
        StandardBom rootBom = standardBomRepository.findById(bomId)
                .orElseThrow(() -> new EntityNotFoundException("BOM not found"));

        Map<String, BigDecimal> materialQuantities = new HashMap<>();
        collectMaterials(rootBom, materialQuantities, new HashSet<>());
        return materialQuantities;
    }

    private void collectMaterials(StandardBom bom, Map<String, BigDecimal> materialQuantities, Set<Long> visited) {
        if (bom == null || visited.contains(bom.getId())) {
            return;
        }
        visited.add(bom.getId());

        for (StandardBomMaterial bomMaterial : bom.getBomMaterials()) {
            String materialCode = bomMaterial.getMaterial().getMaterialCode();
            BigDecimal quantity = bomMaterial.getQuantity();

            // 손실율 적용
            Double lossRate = bomMaterial.getLossRate();
            if (lossRate != null && lossRate > 0) {
                quantity = quantity.multiply(BigDecimal.valueOf(1 + lossRate / 100));
            }

            materialQuantities.merge(materialCode, quantity, BigDecimal::add);
        }

        for (StandardBom childBom : bom.getChildBoms()) {
            collectMaterials(childBom, materialQuantities, visited);
        }
    }


    @Override
    public StandardBomDTO createStandardBom(@Valid StandardBomDTO standardBomDTO) {
        // BOM 유효성 검사
        validateBomDTO(standardBomDTO);

        // StandardBom 엔티티로 변환 및 저장
        StandardBom savedBom = standardBomRepository.save(convertToEntity(standardBomDTO));

        // 자재 목록이 포함된 경우, 중간 엔티티(StandardBomMaterial) 생성 및 저장
        if (standardBomDTO.getBomMaterials() != null && !standardBomDTO.getBomMaterials().isEmpty()) {
            List<StandardBomMaterial> bomMaterials = standardBomDTO.getBomMaterials().stream()
                    .map(bomMaterialDTO -> {
                        // MaterialData 조회
                        MaterialData materialData = materialDataRepository.findById(bomMaterialDTO.getMaterialId())
                                .orElseThrow(() -> new EntityNotFoundException("ID: " + bomMaterialDTO.getMaterialId() + "에 해당하는 자재를 찾을 수 없습니다."));

                        // Product 조회 (품목)
                        Product product = productRepository.findById(bomMaterialDTO.getProductId())
                                .orElseThrow(() -> new EntityNotFoundException("ID: " + bomMaterialDTO.getProductId() + "에 해당하는 품목을 찾을 수 없습니다."));

                        // StandardBomMaterial 빌드
                        return StandardBomMaterial.builder()
                                .bom(savedBom)  // 특정 BOM 참조
                                .product(product) // 품목 참조
                                .material(materialData)  // 자재 참조
                                .quantity(bomMaterialDTO.getQuantity())
                                .lossRate(bomMaterialDTO.getLossRate())
                                .build();
                    })
                    .toList();

            // StandardBomMaterial 저장
            standardBomMaterialRepository.saveAll(bomMaterials);
        }

        // BOM 저장 후 DTO 반환
        return convertToDTO(savedBom);
    }

    private void validateBomDTO(StandardBomDTO standardBomDTO) {
        if (standardBomDTO.getBomCode() == null || standardBomDTO.getBomCode().trim().isEmpty()) {
            throw new IllegalArgumentException("BOM 코드를 입력해 주세요.");
        }

        if (standardBomDTO.getVersion() == null) {
            throw new IllegalArgumentException("BOM 버전을 입력해 주세요.");
        }

        if (standardBomDTO.getLossRate() < 0) {
            throw new IllegalArgumentException("손실율은 0 이상이어야 합니다.");
        }

        if (standardBomDTO.getStartDate() != null && standardBomDTO.getExpiredDate() != null &&
                standardBomDTO.getStartDate().isAfter(standardBomDTO.getExpiredDate())) {
            throw new IllegalArgumentException("유효 시작일은 종료일 이전이어야 합니다.");
        }
    }



    // 기본적으로 부모-자식 관계에서 false로 시작
    private StandardBomDTO convertToDTO(StandardBom standardBom) {
        return convertToDTO(standardBom, false);  // 부모가 아닌 경우
    }


    @Override
    public StandardBomDTO updateStandardBom(Long id, StandardBomDTO updatedBomDTO) {
        // 기존 BOM 조회
        StandardBom existingBom = standardBomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("요청하신 BOM을 찾을 수 없습니다."));

        validateBomDTO(updatedBomDTO);

        // 상위 품목 처리: 상위 품목 없으면 null, 예외 처리 없이 정상 수정 가능
        Product parentProduct = productRepository.findById(updatedBomDTO.getParentProductId()).orElse(null);

        StandardBom newBomVersion = existingBom.toBuilder()
                .code(updatedBomDTO.getBomCode())
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
        List<StandardBomMaterial> bomMaterials = Optional.ofNullable(updatedBomDTO.getBomMaterials())
                .orElse(Collections.emptyList())
                .stream()
                .map(materialDTO -> {
                    MaterialData materialData = materialDataRepository.findById(materialDTO.getMaterialId())
                            .orElseThrow(() -> new EntityNotFoundException("자재를 찾을 수 없습니다."));
                    return StandardBomMaterial.builder()
                            .bom(newBomVersion)
                            .material(materialData)
                            .quantity(materialDTO.getQuantity())
                            .lossRate(materialDTO.getLossRate())
                            .build();
                })
                .collect(Collectors.toList());
        newBomVersion.setBomMaterials(bomMaterials);

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

    public List<StandardBomDTO> getChildBoms(Long parentProductId) {

        if (!productRepository.existsById(parentProductId)) {
            throw new EntityNotFoundException("해당 상위 품목을 찾을 수 없습니다.");
        }

        List<StandardBom> childBoms = standardBomRepository.findByParentProductId(parentProductId);

        return childBoms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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

    // 역전개를 위한 하위 품목으로 상위 BOM 구성 조회 ( 여러 개일 경우 )
    public List<StandardBomDTO> getParentBoms(Long childProductId) {
        List<StandardBom> parentBoms = standardBomRepository.findByChildProductId(childProductId);

        if (parentBoms.isEmpty())
            throw new EntityNotFoundException("해당 하위 품목에 등록된 상위 품목이 없습니다.");

        return parentBoms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    private StandardBom convertToEntity(StandardBomDTO standardBomDTO) {

        // 현재 BOM의 현재 품목 설정
        Product currentProduct = productRepository.findById(standardBomDTO.getCurrentProductId())
                .orElseThrow(() -> new EntityNotFoundException("현재 품목을 찾을 수 없습니다."));

        // 상위 BOM 설정
        StandardBom parentBom = Optional.ofNullable(standardBomDTO.getParentBom()).map(this::convertToEntity).orElse(null);

        // 하위 BOM 설정
        List<StandardBom> childBoms = Optional.ofNullable(standardBomDTO.getChildBoms()).orElse(Collections.emptyList()).stream().map(this::convertToEntity).toList();

        // 자재 목록 설정
        List<StandardBomMaterial> bomMaterials = Optional.ofNullable(standardBomDTO.getBomMaterials())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::convertToBomMaterial)
                .collect(Collectors.toList());

        // StandardBom 엔티티 생성
        return StandardBom.builder()
                .id(standardBomDTO.getId())
                .code(standardBomDTO.getBomCode())
                .version(standardBomDTO.getVersion())
                .createdDate(LocalDateTime.now())
                .remarks(standardBomDTO.getRemarks())
                .lossRate(standardBomDTO.getLossRate())
                .outsourcingType(standardBomDTO.getOutsourcingType())
                .startDate(standardBomDTO.getStartDate())
                .expiredDate(standardBomDTO.getExpiredDate())
                .isActive(standardBomDTO.getIsActive())
                .bomMaterials(bomMaterials)
                .childBoms(childBoms)
                .parentBom(parentBom)
                .build();
    }

    private StandardBomMaterial convertToBomMaterial(BomMaterialDTO materialDTO) {
        MaterialData material = materialDataRepository.findById(materialDTO.getMaterialId())
                .orElseThrow(() -> new EntityNotFoundException("자재를 찾을 수 없습니다."));

        return StandardBomMaterial.builder()
                .material(material)
                .quantity(materialDTO.getQuantity())
                .lossRate(materialDTO.getLossRate())
                .build();
    }

    private StandardBomDTO convertToDTO(StandardBom standardBom, boolean isParent) {
        List<BomMaterialDTO> materialDTOs = Optional.ofNullable(standardBom.getBomMaterials())
                .orElse(Collections.emptyList())
                .stream()
                .map(material -> BomMaterialDTO.builder()
                        .id(material.getId())
                        .bomId(material.getBom().getId())
                        .version(material.getBom().getVersion())
                        .materialId(material.getMaterial().getId())
                        .materialName(material.getMaterial().getMaterialName())
                        .quantity(material.getQuantity())
                        .lossRate(material.getLossRate())
//                        .unitOfMeasure(material.getMaterial().getUnitOfMeasure()) // 단위 추가
//                        .substituteMaterialId(material.getMaterial().getSubstituteMaterialId())
//                        .substituteMaterialName(material.getMaterial().getSubstituteMaterialName())
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
                    .map(bom -> convertToDTO(bom, false)) // 부모가 아닐 때만 자식 처리
                    .toList();
        }

        return StandardBomDTO.builder()
                .id(standardBom.getId())
                .bomCode(standardBom.getCode())
                .version(standardBom.getVersion())
                .createdDate(standardBom.getCreatedDate())
                .remarks(standardBom.getRemarks())
                .lossRate(standardBom.getLossRate())
                .outsourcingType(standardBom.getOutsourcingType())
                .startDate(standardBom.getStartDate())
                .expiredDate(standardBom.getExpiredDate())
                .isActive(standardBom.getIsActive())
                .level(standardBom.getLevel())  // 추가된 level 필드 매핑
                .parentProductId(Optional.ofNullable(standardBom.getParentProduct()).map(Product::getId).orElse(null))
                .currentProductId(Optional.ofNullable(standardBom.getChildProduct()).map(Product::getId).orElse(null)) // TODO currentProduct
                .currentProductCode(Optional.ofNullable(standardBom.getChildProduct()).map(Product::getCode).orElse(null)) // 추가된 currentProductCode 매핑 // TODO currentProduct
                .currentProductName(Optional.ofNullable(standardBom.getChildProduct()).map(Product::getName).orElse(null)) // 추가된 currentProductName 매핑 // TODO currentProduct
                .parentProductCode(Optional.ofNullable(standardBom.getParentProduct()).map(Product::getCode).orElse(null)) // 추가된 parentProductCode 매핑
                .parentProductName(Optional.ofNullable(standardBom.getParentProduct()).map(Product::getName).orElse(null)) // 추가된 parentProductName 매핑
                .childProductId(Optional.ofNullable(standardBom.getChildProduct()).map(Product::getId).orElse(null)) // 추가된 childProductId 매핑
                .childProductCode(Optional.ofNullable(standardBom.getChildProduct()).map(Product::getCode).orElse(null)) // 추가된 childProductCode 매핑
                .childProductName(Optional.ofNullable(standardBom.getChildProduct()).map(Product::getName).orElse(null)) // 추가된 childProductName 매핑
                .bomMaterials(materialDTOs)
                .parentBom(parentBomDTO)
                .childBoms(childBomDTOs)
                .build();
    }

    private <T> T getNullableValue(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<StandardBomDTO> forwardExplode(Long bomId) {
        StandardBom rootBom = standardBomRepository.findById(bomId)
                .orElseThrow(() -> new EntityNotFoundException("BOM 정전개 데이터를 찾을 수 없습니다."));

        List<StandardBomDTO> result = new ArrayList<>();
        traverseChildBoms(rootBom, result, new HashSet<>());
        return result;
    }

    private void traverseChildBoms(StandardBom bom, List<StandardBomDTO> result, Set<Long> visited) {
        if (bom == null || visited.contains(bom.getId())) {
            return;
        }
        visited.add(bom.getId());

        result.add(convertToDTO(bom)); // 현재 BOM 추가

        for (StandardBom childBom : bom.getChildBoms()) {
            traverseChildBoms(childBom, result, visited);
        }
    }

    public List<StandardBomDTO> backwardExplode(Long bomId) {
        StandardBom bom = standardBomRepository.findById(bomId)
                .orElseThrow(() -> new EntityNotFoundException("BOM 역전개 데이터를 찾을 수 없습니다."));

        List<StandardBomDTO> result = new ArrayList<>();
        traverseParentBoms(bom, result, new HashSet<>());
        return result;
    }

    private void traverseParentBoms(StandardBom bom, List<StandardBomDTO> result, Set<Long> visited) {
        if (bom == null || visited.contains(bom.getId())) {
            return;
        }
        visited.add(bom.getId());

        result.add(convertToDTO(bom)); // 현재 BOM 추가

        StandardBom parentBom = bom.getParentBom();
        if (parentBom != null) {
            traverseParentBoms(parentBom, result, visited);
        }
    }


}
