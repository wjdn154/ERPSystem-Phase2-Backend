package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product_group.ProductGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService{

    private final ProductGroupRepository productGroupRepository;
    private final ProductRepository productRepository;

    /**
     * 모든 품목 그룹 조회
     * @return 모든 품목 그룹 DTO를 List로 반환
     */
    @Override
    public List<ProductGroupDto> findAllProductGroups() {

        return productGroupRepository.findAll().stream()
                                            .map(this::toDto)
                                            .collect(Collectors.toList());
    }

    /**
     * 특정 id로 품목 그룹 조회
     * @param id
     * @return 특정 id를 가진 폼목 그룹 DTO 반환
     */
    @Override
    public ProductGroupDto getProductGroupById(Long id) {
        return productGroupRepository.findById(id)
                                        .map(this::toDto)
                                        .orElseThrow(() -> new IllegalArgumentException("해당 ID의 품목 그룹을 찾을 수가 없습니다." + id));
    }

    /**
     * 폼목 그룹 등록
     * @param productGroupDto
     * @return 등록된 품목 그룹 DTO를 반환
     */
    @Override
    public Optional<ProductGroupDto> saveProductGroup(ProductGroupDto productGroupDto) {
        // code와 name에 대한 유효성 검증(null 이거나 비어있지 않은지 확인)
        validateProductGroup(productGroupDto);

        if (productGroupRepository.existsByCode(productGroupDto.getCode())) {
            throw new IllegalArgumentException("해당 코드로 등록된 품목 그룹이 이미 존재합니다.");
        }

        ProductGroup productGroup = ProductGroup.builder()
                                                .code(productGroupDto.getCode())
                                                .name(productGroupDto.getName())
                                                .build();
        ProductGroup savedGroup = productGroupRepository.save(productGroup);

        return Optional.of(toDto(savedGroup));
    }

    /**
     * 등록된 품목 그룹 수정하기
     * @param id
     * @param productGroupDto
     * @return
     */
    @Override
    public Optional<ProductGroupDto> updateProduct(Long id, ProductGroupDto productGroupDto) {

        validateProductGroup(productGroupDto);

        Optional<ProductGroup> findProductGroup = productGroupRepository.findById(id);

        if (findProductGroup.isPresent()) {
            ProductGroup productGroup = findProductGroup.get();

            if (productGroupRepository.existsByCodeAndIdNot(productGroupDto.getCode(), id)) {
                throw new IllegalArgumentException("해당 코드로 등록된 품목 그룹이 이미 존재합니다.");
            }

            productGroup.setCode(productGroupDto.getCode());
            productGroup.setName(productGroupDto.getName());

            ProductGroup updatedProductGroup = productGroupRepository.save(productGroup);

            return Optional.of(toDto(updatedProductGroup));
        } else {
            return Optional.empty();
        }

    }

    /**
     * 품목 그룹 삭제
     * @param id
     * @return 삭제 완료 유무 문자열 반환
     */
    @Override
    public String deleteProductGroup(Long id) {
        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제 실패 : 삭제하려는 품목 그룹의 ID를 찾을 수 없습니다."));

        // 삭제 전 Product 의 productGroup 필드를 null로 설정
        productRepository.nullifyProductGroupId(id);

        productGroupRepository.delete(productGroup);
        return productGroup.getName() + " 품목 그룹이 삭제되었습니다.";
    }

    @Override
    public String deactivateProductGroup(Long id) {
        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 품목 그룹을 찾을 수 없습니다."));

        // 품목 그룹 사용중단
        productGroup.deactivate();
        productGroupRepository.save(productGroup);

        // Product 의 productGroup 필드를 null로 설정
        productRepository.nullifyProductGroupId(id);

        return productGroup.getName() + " 품목 그룹이 사용 중단되었습니다.";
    }

    @Override
    public String reactivateProductGroup(Long id) {
        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 품목 그룹을 찾을 수 없습니다."));

        // 품목 그룹 재사용
        productGroup.reactivate();
        productGroupRepository.save(productGroup);

        // Product의 productGroup 필드를 다시 id 로 설정
//        productRepository.updateStatusByProductGroupId(id);
        return null;
    }

    // code와 name에 대한 유효성 검증 메소드
    private static void validateProductGroup(ProductGroupDto productGroupDto) {
        if (productGroupDto.getCode() == null || productGroupDto.getCode().isEmpty()) {
            throw new IllegalArgumentException("코드를 입력해주세요.");
        }
        if (productGroupDto.getName() == null || productGroupDto.getName().isEmpty()) {
            throw new IllegalArgumentException("품목 그룹명을 입력해주세요.");
        }
    }

    // Entity -> DTO 변환 메소드
    private ProductGroupDto toDto(ProductGroup productGroup) {
        return ProductGroupDto.builder()
                .id(productGroup.getId())
                .code(productGroup.getCode())
                .name(productGroup.getName())
                .build();
    }

}
