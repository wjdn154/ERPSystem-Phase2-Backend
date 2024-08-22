package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductSaveRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductSaveResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.Product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    /**
     * 품목등록 리스트 조회
     * @return 등록된 모든 품목을 반환
     */
    @PostMapping("/api/logistics/product-list")
    public ResponseEntity<List<ProductDto>> getAllProductList() {
        // 서비스에서 등록된 모든 품목 리스트를 가져옴
        List<ProductDto> response = productService.findAllProducts();
        // HTTP 200 상태로 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 등록된 각 품목의 상세 정보 조회
     * @return 특정 id에 해당하는 품목의 상세 정보 조회
     */
    @PostMapping("api/logistics/product-detail/{id}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable("id") Long id) {
        ProductDetailDto response = productService.findProductDetailById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 품목 등록
     * @param productSaveRequestDto
     * @return 등록된 품목의 DTO를 반환
     */
    @PostMapping("api/logistics/save-product")
    public ResponseEntity<ProductSaveResponseDto> saveProduct(@RequestBody ProductSaveRequestDto productSaveRequestDto) {
        try {
            return productService.saveProduct(productSaveRequestDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() ->  ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

//    @PutMapping("api/logistics/update-")

}
