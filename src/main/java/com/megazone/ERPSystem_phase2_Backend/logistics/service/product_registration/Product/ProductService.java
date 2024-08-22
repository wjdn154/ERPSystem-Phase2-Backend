package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.Product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductSaveRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductSaveResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAllProducts();

    ProductDetailDto findProductDetailById(Long id);

    Optional<ProductSaveResponseDto> saveProduct(ProductSaveRequestDto productSaveRequestDto);
}
