package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.Product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductSaveRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductSaveResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.Product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductGroupRepository productGroupRepository;

    /**
     * 품목등록 리스트 조회
     * @return 등록된 모든 품목을 반환
     */
    @Override
    public List<ProductDto> findAllProducts() {

        return productRepository.findAll().stream()
                .map(ProductDto::createProductDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 id 값을 가진 품목의 상세 정보 조회하기
     * @param id
     * @return id가 일치한 품목의 상세 정보 dto 반환
     */
    @Override
    public ProductDetailDto findProductDetailById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("품목을 찾을 수 없습니다."));
        return ProductDetailDto.createProductDetailDto(product);
    }

    /**
     * 새로운 품목 등록하기
     * @param productSaveRequestDto 저장할 품목의 정보가 담긴 DTO
     * @return 저장된 품목 정보를 담은 DTO를 Optional로 반환함.
     */
    @Override
    public Optional<ProductSaveResponseDto> saveProduct(ProductSaveRequestDto productSaveRequestDto) {
        // 코드 중복 검사
        if (productRepository.existsByCode(productSaveRequestDto.getCode())){
            throw new IllegalArgumentException("입력하신 코드로 등록된 품목이 이미 존재합니다.");
        }

        // 품목 그룹 조회
        Optional<ProductGroup> productGroup = productGroupRepository.findById(productSaveRequestDto.getProductGroupId());
        if (productGroup.isEmpty()) {
            return Optional.empty(); // 폼목 그룹 없으면 빈 Optional 반홤함
        }

        // 엔티티로 변환 후 저장
        Product product = productSaveRequestDto.toEntity(productGroup.get());
        Product savedproduct = productRepository.save(product);

        // 다시 DTO로 변환 후 반환
        return Optional.of(toDto(savedproduct));
    }

    private ProductSaveResponseDto toDto(Product product) {
        return ProductSaveResponseDto.builder()
                .code(product.getCode())
                .name(product.getName())
                .productGroupName(product.getProductGroup().getName())
                .standard(product.getStandard())
                .unit(product.getUnit())
                .purchasePrice(product.getPurchasePrice())
                .salesPrice(product.getSalesPrice())
                .productType(product.getProductType())
                .productionProcessId(product.getProductionProcessId())
                .build();
    }
}
