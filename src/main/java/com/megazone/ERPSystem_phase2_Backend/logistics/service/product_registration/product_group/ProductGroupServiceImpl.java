package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product_group;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
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
public class ProductGroupServiceImpl implements ProductGroupService{

    private final ProductGroupRepository productGroupRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    /**
     * 특정 회사에 속한 품목 그룹 조회 (검색어 필터 적용)
     * @param companyId 회사 ID
     * @param searchTerm 검색어
     * @return 해당 회사의 품목 그룹 리스트
     */
    public List<ProductGroupDto> findAllProductGroups(Long companyId, String searchTerm) {
        return productGroupRepository.findProductGroupsByCompanyAndSearchTerm(companyId, searchTerm)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 폼목 그룹 등록
     *
     * @param companyId
     * @param productGroupDto
     * @return 등록된 품목 그룹 DTO를 반환
     */
    @Override
    public Optional<ProductGroupDto> saveProductGroup(Long companyId, ProductGroupDto productGroupDto) {

        validateProductGroup(productGroupDto);
        checkProductGroupCode(productGroupDto.getCode());

        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            return Optional.empty();
        }

        ProductGroup productGroup = toEntity(productGroupDto, company.get());
        ProductGroup savedGroup = productGroupRepository.save(productGroup);

        return Optional.of(toDto(savedGroup));
    }

    /**
     * 등록된 품목 그룹 수정하기
     *
     * @param companyId
     * @param id
     * @param productGroupDto
     * @return
     */
    @Override
    public Optional<ProductGroupDto> updateProduct(Long companyId, Long id, ProductGroupDto productGroupDto) {
        validateProductGroup(productGroupDto);

        validateCompanyExistence(companyId);

        ProductGroup productGroup = productGroupRepository.findById(id).orElse(null);
        if (productGroup == null) {
            return Optional.empty();
        }
        if (productGroupRepository.existsByCodeAndIdNot(productGroupDto.getCode(), id)) {
            throw new IllegalArgumentException("해당 코드로 등록된 품목 그룹이 이미 존재합니다.");
        }

        // 필드 업데이트
        productGroup.setCompany(productGroup.getCompany());
        productGroup.setCode(productGroupDto.getCode());
        productGroup.setName(productGroupDto.getName());

        // 저장 및 DTO 변환
        ProductGroup updatedProductGroup = productGroupRepository.save(productGroup);
        return Optional.of(toDto(updatedProductGroup));
    }

    /**
     * 품목 그룹 삭제
     *
     * @param companyId
     * @param id
     * @return 삭제 완료 유무 문자열 반환
     */
    @Override
    public String deleteProductGroup(Long companyId, Long id) {
        validateCompanyExistence(companyId);

        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제 실패 : 삭제하려는 품목 그룹의 ID를 찾을 수 없습니다."));

        // 삭제 전 Product 의 productGroup 필드를 null 로 설정
        productRepository.nullifyProductGroupId(id);

        productGroupRepository.delete(productGroup);
        return productGroup.getName() + " 품목 그룹이 삭제되었습니다.";
    }

    /**
     * 주어진 ID를 기준으로 품목 그룹을 사용중단.
     *
     * @param companyId
     * @param id         사용중단할 품목 그룹의 ID.
     * @return 품목 그룹의 사용중단 상태를 나타내는 메시지.
     * @throws IllegalArgumentException 주어진 ID로 품목 그룹을 찾을 수 없는 경우.
     */
    @Override
    public String deactivateProductGroup(Long companyId, Long id) {
        validateCompanyExistence(companyId);

        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 품목 그룹을 찾을 수 없습니다."));

        // 품목 그룹 사용중단
        productGroup.deactivate();
        productGroupRepository.save(productGroup);

        return productGroup.getName() + " 품목 그룹이 사용 중단되었습니다.";
    }

    /**
     * 주어진 ID를 기준으로 품목 그룹을 재사용.
     *
     * @param companyId
     * @param id        재사용할 품목 그룹의 ID.
     * @return 품목 그룹의 재사용 상태를 나타내는 메시지.
     * @throws IllegalArgumentException 주어진 ID로 품목 그룹을 찾을 수 없는 경우.
     */
    @Override
    public String reactivateProductGroup(Long companyId, Long id) {
        validateCompanyExistence(companyId);

        ProductGroup productGroup = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 품목 그룹을 찾을 수 없습니다."));

        productGroup.reactivate();
        productGroupRepository.save(productGroup);

        return productGroup.getName() + " 품목 그룹을 재사용합니다.";
    }

    // 회사 ID의 존재 여부를 확인하는 메서드
    private void validateCompanyExistence(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new IllegalArgumentException("존재하지 않는 회사 ID 입니다.");
        }
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

    // 주어진 코드가 이미 존재하는지 확인하고, 존재할 경우 예외를 발생시키는 메서드
    private void checkProductGroupCode(String code) {
        if (productGroupRepository.existsByCode(code)) {
            throw new IllegalArgumentException("해당 코드로 등록된 품목 그룹이 이미 존재합니다.");
        }
    }

    // DTO -> Entity 변환 메소드
    private ProductGroup toEntity(ProductGroupDto productGroupDto, Company company) {
        return ProductGroup.builder()
                .company(company)
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
