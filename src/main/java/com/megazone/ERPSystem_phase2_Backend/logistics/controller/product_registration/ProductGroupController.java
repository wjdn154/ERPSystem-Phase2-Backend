package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product_group.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/product-groups")
public class ProductGroupController {

    private final ProductGroupService productGroupService;

    /**
     * 품목 그룹 리스트 조회 (검색어 포함)
     * @param searchTerm 검색어
     * @return 모든 품목 그룹 리스트를 반환
     */
    @PostMapping("/")
    public ResponseEntity<List<ProductGroupDto>> getAllProductGroups(@RequestParam(value = "search", required = false) String searchTerm) {

        List<ProductGroupDto> response = productGroupService.findAllProductGroups(searchTerm);

        return ResponseEntity.ok(response);
    }

    /**
     * 품목 그룹을 등록함
     * @param productGroupDto 등록할 품목 그룹 정보
     * @return 등록된 품목 그룹의 DTO를 반환
     */
    @PostMapping("/save")
    public ResponseEntity<ProductGroupDto> saveProductGroup(@RequestBody ProductGroupDto productGroupDto) {

        return productGroupService.saveProductGroup(productGroupDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * 등록된 품목 그룹을 업데이트함
     * @param id 업데이트하려는 품목 그룹의 ID
     * @param productGroupDto 업데이트할 품목 그룹 정보
     * @return 업데이트된 품목 그룹의 DTO를 반환
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductGroupDto> updateProductGroup(@PathVariable("id") Long id, @RequestBody ProductGroupDto productGroupDto) {

        return productGroupService.updateProduct(id, productGroupDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * 등록된 품목 그룹을 삭제함
     * @param id 삭제할 품목 그룹의 ID
     * @return 품목 그룹 삭제 성공 여부를 문자열로 반환
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductGroup(@PathVariable("id") Long id) {

        String result = productGroupService.deleteProductGroup(id);

        return ResponseEntity.ok(result);
    }

    /**
     * ID를 통해 품목 그룹을 사용 중단함
     * @param id 사용 중단할 품목 그룹의 ID
     * @return 사용 중단 처리 결과를 담은 응답 엔티티
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateProductGroup(@PathVariable("id") Long id) {

        String result = productGroupService.deactivateProductGroup(id);

        return ResponseEntity.ok(result);
    }

    /**
     * ID를 통해 품목 그룹을 재사용
     * @param id 다시 재사용할 품목 그룹의 ID
     * @return 재사용할 처리 결과를 담은 응답 엔티티
     */
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<String> reactivateProductGroup(@PathVariable("id") Long id) {

        String result = productGroupService.reactivateProductGroup(id);

        return ResponseEntity.ok(result);
    }

}