package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    /**
     * 품목등록 리스트 조회
     * @return 등록된 모든 품목을 반환
     */
    @PostMapping("/product-list")
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
    @PostMapping("/product-detail/{id}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable("id") Long id) {
        try {
            ProductDetailDto response = productService.findProductDetailById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 품목을 등록함
     * @param productSaveRequestDto
     * @return 등록된 품목의 DTO를 반환
     */
    @PostMapping("/save-product")
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

    /**
     * 품목 업데이트
     * @param id 업데이트하려는 품목의 id
     * @param productSaveRequestDto 업데이트할 품목 정보
     * @return 업데이트된 품목 정보를 반환함
     */
    @PutMapping("/update-product/{id}")
    public ResponseEntity<ProductSaveResponseDto> updateProduct(@PathVariable("id") Long id,
                                                                @RequestBody ProductSaveRequestDto productSaveRequestDto){
        try{
            return productService.updateProduct(id, productSaveRequestDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        try {
            String result = productService.deleteProduct(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("품목 상제 중 오류가 발생했습니다.");
        }
    }

}
