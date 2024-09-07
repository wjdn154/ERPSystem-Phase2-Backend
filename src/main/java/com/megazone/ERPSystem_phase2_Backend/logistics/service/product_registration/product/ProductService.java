package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAllProducts(Long company_id);

    ProductDetailDto findProductDetailById(Long id);

    Optional<ProductResponseDto> saveProduct(ProductRequestDto productRequestDto);

    Optional<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequestDto);

    String deleteProduct(Long id);

    String deactivateProduct(Long id);

    String reactivateProduct(Long id);
}
