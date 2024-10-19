package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product.ProductImageService;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/products")
public class ProductController {
    private final ProductService productService;
    private final ProductImageService productImageService;
    
    /**
     * 품목등록 리스트 조회
     * @return 등록된 모든 품목을 반환
     */
    @PostMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProductList() {

        List<ProductResponseDto> response = productService.findAllProducts();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 등록된 각 품목의 상세 정보 조회
     * @return 특정 id에 해당하는 품목의 상세 정보 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductDetailById(@PathVariable("id") Long id) {

        return productService.findProductDetailById(id)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }


    /**
     * 품목을 등록함
     * @param productData
     * @param imageFile
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/save")
    public ResponseEntity<ProductResponseDto> saveProduct(@RequestParam("productData") String productData,
                                                          @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws JsonProcessingException {

        // JSON 문자열을 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDto productRequestDto = objectMapper.readValue(productData, ProductRequestDto.class);

        return productService.saveProduct(productRequestDto, imageFile)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    /**
     * 품목 업데이트
     * @param id 업데이트하려는 품목의 id
     * @param productData JSON 형식의 품목 정보
     * @param imageFile 이미지 파일 (선택 사항)
     * @return 업데이트된 품목 정보를 반환함
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @RequestParam("productData") String productData,
                                                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws JsonProcessingException {

        // JSON 문자열을 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDto productRequestDto = objectMapper.readValue(productData, ProductRequestDto.class);

        return productService.updateProduct(id, productRequestDto, imageFile)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    /**
     * 지정된 ID의 품목 삭제
     *
     * @param id 삭제할 품목의 ID
     * @return 결과 메시지를 포함하는 응답 엔티티
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){

        String result = productService.deleteProduct(id);

        return ResponseEntity.ok(result);

    }

    /**
     * ID를 통해 품목 사용중단.
     *
     * @param id 사용중단할 품목 그룹의 ID
     * @return 사용중단 처리 결과를 담은 응답 엔티티
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable("id") Long id) {

        String result = productService.deactivateProduct(id);

        return ResponseEntity.ok(result);
    }

    /**
     * ID를 통해 품목을 재사용
     *
     * @param id 다시 재사용할 품목의 ID
     * @return 재사용할 처리 결과를 담은 응답 엔티티
     */
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<String> reactivateProductGroup(@PathVariable("id") Long id) {

        String result = productService.reactivateProduct(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get("src/main/resources/static/uploads/").resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                // Content-Type을 파일의 MIME 타입으로 설정
                String contentType = Files.probeContentType(file);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
