package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductResponseDto> findAllProducts(Long company_id);

    Optional<ProductResponseDto> findProductDetailById(Long companyId, Long id);

    Optional<ProductResponseDto> saveProduct(Long companyId, ProductRequestDto productRequestDto);

    Optional<ProductResponseDto> updateProduct(Long companyId, Long id, ProductRequestDto productRequestDto);

    String deleteProduct(Long companyId, Long id);

    String deactivateProduct(Long companyId, Long id);

    String reactivateProduct(Long companyId, Long id);
}
