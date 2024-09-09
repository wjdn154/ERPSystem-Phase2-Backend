package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product_group.ProductGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductionRoutingRepository productionRoutingRepository;
    private final ProductGroupRepository productGroupRepository;
    private final CompanyRepository companyRepository;

    /**
     * 품목등록 리스트 조회
     * @return 등록된 모든 품목을 반환
     * 리펙토링 해야함
     */
    @Override
    public List<ProductResponseDto> findAllProducts(Long companyId) {

        // 회사 존재 여부 검사
        validateCompanyExistence(companyId);

        return productRepository.findAllByCompanyId(companyId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 id 값을 가진 품목의 상세 정보 조회하기
     *
     * @param companyId
     * @param id
     * @return id가 일치한 품목의 상세 정보 dto 반환
     */
    @Override
    public Optional<ProductResponseDto> findProductDetailById(Long companyId, Long id) {

        // 회사 존재 여부 검사
        validateCompanyExistence(companyId);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("품목을 찾을 수 없습니다."));

        return Optional.of(toDto(product));
    }

    /**
     * 새로운 품목 등록하기
     *
     * @param companyId
     * @param productRequestDto 저장할 품목의 정보가 담긴 DTO
     * @return 저장된 품목 정보를 담은 DTO를 Optional로 반환함.
     */
    @Override
    public Optional<ProductResponseDto> saveProduct(Long companyId, ProductRequestDto productRequestDto) {

        // 회사 존재 여부 검사
        validateCompanyExistence(companyId);
        // 코드 중복 검사
        validateProductCodeUnique(productRequestDto.getCode());

        // 품목 그룹 조회
        Optional<ProductGroup> productGroup = productGroupRepository.findById(productRequestDto.getProductGroupId());
        if (productGroup.isEmpty()) {
            return Optional.empty(); // 폼목 그룹 없으면 빈 Optional 반홤함
        }

        // 생산 라우팅 조회
        Optional<ProductionRouting> productionRouting = productionRoutingRepository.findById(productRequestDto.getProductionRoutingId());
        if (productionRouting.isEmpty()) {
            return Optional.empty(); // 생산 라우팅 없으면 빈 Optional 반홤함
        }

        // 엔티티로 변환 후 저장
        Product product = toEntity(productRequestDto, productGroup.get(), productionRouting.get());
        Product savedProduct = productRepository.save(product);

        // 다시 DTO로 변환 후 반환
        return Optional.of(toDto(savedProduct));
    }

    /**
     * 등록된 품목 수정하기
     * @param companyId
     * @param id
     * @param productRequestDto
     * @return 수정된 품목의 DTO를 반환
     */
    @Override
    public Optional<ProductResponseDto> updateProduct(Long companyId, Long id, ProductRequestDto productRequestDto) {

        // 회사 존재 여부 검사
        validateCompanyExistence(companyId);

        // 품목 조회 및 검증
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("품목을 찾을 수 없습니다."));

        // 코드 중복 검사, 업데이트 할 품목은 제외하고 검사
        if (productRepository.existsByCodeAndIdNot(productRequestDto.getCode(), id)) {
            throw new IllegalArgumentException("동일한 코드를 가진 품목이 이미 존재합니다.");
        }

        // 품목 그룹 조회 및 업데이트
        ProductGroup productGroup = productGroupRepository.findById(productRequestDto.getProductGroupId())
                .orElseThrow(() -> new IllegalArgumentException("품목 그룹을 찾을 수 없습니다."));

        product.setProductGroup(productGroup);

        // 생산 라우팅 조회 및 업데이트
        ProductionRouting productionRouting = productionRoutingRepository.findById(productRequestDto.getProductionRoutingId())
                .orElseThrow(() -> new IllegalArgumentException("생산 라우팅을 찾을 수 없습니다."));

        product.setProductionRouting(productionRouting);

        // 나머지 필드 업데이트
        updateProductFields(product, productRequestDto);

        // 저장
        Product updatedProduct = productRepository.save(product);
        return Optional.of(toDto(updatedProduct));
    }

    /**
     * 품목 삭제
     * @param companyId
     * @param id
     * @return 삭제 완료 유무 문자열 반환
     */
    @Override
    public String deleteProduct(Long companyId, Long id) {

        // 회사 존재 여부 검사
        validateCompanyExistence(companyId);

        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return product.getName() + " 품목이 삭제되었습니다.";
                })
                .orElse("삭제 실패 : 삭제하려는 품목의 ID를 찾을 수 없습니다.");

    }

    /**
     * 주어진 ID를 기준으로 품목을 사용중단.
     *
     * @param companyId
     * @param id        사용중단할 품목의 ID.
     * @return 품목의 사용중단 상태를 나타내는 메시지.
     * @throws IllegalArgumentException 주어진 ID로 품목을 찾을 수 없는 경우.
     */
    @Override
    public String deactivateProduct(Long companyId, Long id) {

        // 회사 존재 여부 검사
        validateCompanyExistence(companyId);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 품목을 찾을 수 없습니다."));

        product.deactivate();
        productRepository.save(product);

        return product.getName() + " 품목이 사용 중단되었습니다.";
    }

    /**
     * 주어진 ID를 기준으로 품목을 재사용.
     *
     * @param id 재사용할 품목의 ID.
     * @return 품목의 재사용 상태를 나타내는 메시지.
     * @throws IllegalArgumentException 주어진 ID로 품목을 찾을 수 없는 경우.
     */
    @Override
    public String reactivateProduct(Long companyId, Long id) {

        // 회사 존재 여부 검사
        validateCompanyExistence(companyId);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 품목을 찾을 수 없습니다."));

        product.reactivate();
        productRepository.save(product);

        return product.getName() + " 품목을 재사용합니다.";
    }


    // 회사 ID의 존재 여부를 확인하는 메서드
    private void validateCompanyExistence(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new IllegalArgumentException("존재하지 않는 회사 ID 입니다.");
        }
    }

    // 주어진 코드에 해당하는 상품이 이미 존재하는지 확인하는 메서드
    private void validateProductCodeUnique(String code) {
        if (productRepository.existsByCode(code)) {
            throw new IllegalArgumentException("해당 코드로 등록된 품목이 이미 존재합니다.");
        }
    }

    // 나머지 필드 업데이트하는 메서드
    private void updateProductFields(Product product, ProductRequestDto productRequestDto) {
        product.setCode(productRequestDto.getCode());
        product.setName(productRequestDto.getName());
        product.setStandard(productRequestDto.getStandard());
        product.setUnit(productRequestDto.getUnit());
        product.setPurchasePrice(productRequestDto.getPurchasePrice());
        product.setSalesPrice(productRequestDto.getSalesPrice());
        product.setProductType(productRequestDto.getProductType());
    }

    // Entity -> DTO 변환 메서드
    private ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .productGroupCode(product.getProductGroup() != null ? product.getProductGroup().getCode() : null)
                .productGroupName(product.getProductGroup() != null ? product.getProductGroup().getName() : null)
                .standard(product.getStandard())
                .unit(product.getUnit())
                .purchasePrice(product.getPurchasePrice())
                .salesPrice(product.getSalesPrice())
                .productType(product.getProductType())
                .productionRoutingCode(product.getProductionRouting() != null ? product.getProductionRouting().getCode() : null)
                .productionRoutingName(product.getProductionRouting() != null ? product.getProductionRouting().getName() : null)
                .isActive(product.isActive())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public Product toEntity(ProductRequestDto productRequestDto, ProductGroup productGroup, ProductionRouting productionRouting) {
        return Product.builder()
                .code(productRequestDto.getCode())
                .name(productRequestDto.getName())
                .productGroup(productGroup)
                .productionRouting(productionRouting)
                .standard(productRequestDto.getStandard())
                .unit(productRequestDto.getUnit())
                .purchasePrice(productRequestDto.getPurchasePrice())
                .salesPrice(productRequestDto.getSalesPrice())
                .productType(productRequestDto.getProductType())
                .build();
    }

}
