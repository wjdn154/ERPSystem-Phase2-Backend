package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.Product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.Product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
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
     * @param productDetailDto 저장할 품목의 정보가 담긴 DTO
     * @return 저장된 품목 정보를 담은 DTO를 Optional로 반환함.
     */
    @Override
    public Optional<Product> saveProduct(ProductDetailDto productDetailDto) {
        Pro
        return Optional.empty();
    }
}
