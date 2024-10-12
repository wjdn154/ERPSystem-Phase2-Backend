package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductResponseDto> findAllProducts();

    Optional<ProductResponseDto> findProductDetailById(Long id);

    Optional<ProductResponseDto> saveProduct(ProductRequestDto productRequestDto, MultipartFile images);

    Optional<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequestDto, MultipartFile imageFile);

    String deleteProduct(Long id);

    String deactivateProduct(Long id);

    String reactivateProduct(Long id);
}
