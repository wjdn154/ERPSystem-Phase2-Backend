package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductRequestDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/products")
public class ProductController {
    private final ProductService productService;
    private final UsersRepository usersRepository;

    /**
     * 품목등록 리스트 조회
     * @return 등록된 모든 품목을 반환
     */
    @PostMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProductList() {

        Long companyId = getCompanyIdOfAuthenticatedUser();

        List<ProductResponseDto> response = productService.findAllProducts(companyId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 등록된 각 품목의 상세 정보 조회
     * @return 특정 id에 해당하는 품목의 상세 정보 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductDetailById(@PathVariable("id") Long id) {

        Long companyId = getCompanyIdOfAuthenticatedUser();

        return productService.findProductDetailById(companyId, id)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * 품목을 등록함
     * @param productRequestDto
     * @return 등록된 품목의 DTO를 반환
     */
    @PostMapping("/save")
    public ResponseEntity<ProductResponseDto> saveProduct(@RequestBody ProductRequestDto productRequestDto) {

        Long companyId = getCompanyIdOfAuthenticatedUser();

        return productService.saveProduct(companyId, productRequestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    /**
     * 품목 업데이트
     * @param id 업데이트하려는 품목의 id
     * @param productRequestDto 업데이트할 품목 정보
     * @return 업데이트된 품목 정보를 반환함
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDto productRequestDto){

        Long companyId = getCompanyIdOfAuthenticatedUser();

        return productService.updateProduct(companyId, id, productRequestDto)
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

        Long companyId = getCompanyIdOfAuthenticatedUser();

        String result = productService.deleteProduct(companyId, id);

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

        Long companyId = getCompanyIdOfAuthenticatedUser();

        String result = productService.deactivateProduct(companyId, id);

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

        Long companyId = getCompanyIdOfAuthenticatedUser();

        String result = productService.reactivateProduct(companyId, id);

        return ResponseEntity.ok(result);
    }

    /**
     * 사용자 인증 정보를 통해 회사 ID를 가져오는 메서드
     * @return 인증된 사용자의 회사 ID
     */
    private Long getCompanyIdOfAuthenticatedUser() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Users user = usersRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return user.getCompany().getId();
    }
}
