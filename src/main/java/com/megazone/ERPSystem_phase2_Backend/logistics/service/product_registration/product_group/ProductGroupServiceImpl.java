package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product_group;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
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
public class ProductGroupServiceImpl implements ProductGroupService {
    private final ProductGroupRepository productGroupRepository;
    private final ProductRepository productRepository;

    /**
     * 특정 회사에 속한 품목 그룹 조회 (검색어 필터 적용)
     *
     * @param searchTerm 검색어
     * @return 해당 회사의 품목 그룹 리스트
     */
    @Override
    public List<ProductGroupDto> findAllProductGroups(String searchTerm) {
        return productGroupRepository.findProductGroupsBySearchTerm(searchTerm)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 폼목 그룹 등록
     *
     * @param productGroupDto 품목 그룹 DTO
     * @return 등록된 품목 그룹 DTO를 반환
     */
    @Override
    public Optional<ProductGroupDto> saveProductGroup(ProductGroupDto productGroupDto) {

        // 품목 그룹의 code와 name에 대한 유효성 검증 메서드
        validateProductGroup(productGroupDto.getCode(), productGroupDto.getName());

        ProductGroup productGroup = toEntity(productGroupDto);
        ProductGroup savedGroup = productGroupRepository.save(productGroup);

        return Optional.of(toDto(savedGroup));
    }

    /**
     * 등록된 품목 그룹 수정하기
     *
     * @param id 품목 그룹 ID
     * @param productGroupDto 품목 그룹 DTO
     * @return 수정된 품목 그룹 DTO를 반환
     */
    @Override
    public Optional<ProductGroupDto> updateProduct(Long id, ProductGroupDto productGroupDto) {

        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 품목 그룹을 찾을 수 없습니다."));

        // 품목 그룹의 code와 name에 대한 유효성 검증 메서드
        validateProductGroup(productGroupDto.getCode(), productGroupDto.getName());

        if (productGroupRepository.existsByCodeAndIdNot(productGroupDto.getCode(), id)) {
            throw new IllegalArgumentException("해당 코드로 등록된 품목 그룹이 이미 존재합니다.");
        }

        // 필드 업데이트
        productGroup.setCode(productGroupDto.getCode());
        productGroup.setName(productGroupDto.getName());

        ProductGroup updatedProductGroup = productGroupRepository.save(productGroup);
        return Optional.of(toDto(updatedProductGroup));
    }

    /**
     * 품목 그룹 삭제
     *
     * @param id 품목 그룹 ID
     * @return 삭제 완료 유무 문자열 반환
     */
    @Override
    public String deleteProductGroup(Long id) {

        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제 실패 : 삭제하려는 품목 그룹을 찾을 수 없습니다."));

        // 삭제 전 Product 의 productGroup 필드를 null 로 설정
        productRepository.nullifyProductGroupId(id);

        productGroupRepository.delete(productGroup);
        return productGroup.getName() + " 품목 그룹이 삭제되었습니다.";
    }

    /**
     * 품목 그룹 사용 중단
     *
     * @param id 품목 그룹 ID
     * @return 품목 그룹의 사용 중단 상태를 나타내는 메시지
     */
    @Override
    public String deactivateProductGroup(Long id) {

        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 품목 그룹을 찾을 수 없습니다."));

        productGroup.deactivate();
        productGroupRepository.save(productGroup);
        return productGroup.getName() + " 품목 그룹이 사용 중단되었습니다.";
    }

    /**
     * 품목 그룹 재사용
     *
     * @param id 품목 그룹 ID
     * @return 품목 그룹의 재사용 상태를 나타내는 메시지
     */
    @Override
    public String reactivateProductGroup(Long id) {

        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자의 회사에 해당 품목 그룹을 찾을 수 없습니다."));

        productGroup.reactivate();
        productGroupRepository.save(productGroup);
        return productGroup.getName() + " 품목 그룹을 재사용합니다.";
    }


    // 품목 그룹의 code와 name에 대한 유효성 검증 메서드
    private void validateProductGroup(String code, String name) {
        if (isNullOrBlank(code)) {
            throw new IllegalArgumentException("코드를 입력해주세요.");
        }
        if (isNullOrBlank(name)) {
            throw new IllegalArgumentException("품목 그룹명을 입력해주세요.");
        }
    }

    // 문자열이 null이거나 공백인지 확인하는 메서드
    private boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    // DTO -> Entity 변환 메소드
    private ProductGroup toEntity(ProductGroupDto productGroupDto) {
        return ProductGroup.builder()
                .code(productGroupDto.getCode())
                .name(productGroupDto.getName())
                .build();
    }

    // Entity -> DTO 변환 메소드
    private ProductGroupDto toDto(ProductGroup productGroup) {
        return ProductGroupDto.builder()
                .id(productGroup.getId())
                .code(productGroup.getCode())
                .name(productGroup.getName())
                .isActive(productGroup.isActive())
                .build();
    }
}