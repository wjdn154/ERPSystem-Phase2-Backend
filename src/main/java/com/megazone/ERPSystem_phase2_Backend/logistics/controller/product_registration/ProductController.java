package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.Product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    /**
     * 품목등록 리스트 조회
     * @return 등록된 모든 품목을 반환
     */
    @GetMapping("/api/logistics/product-list")
    public ResponseEntity<ProductDto> getAllProductDetails() {

        return null;
    }

}
