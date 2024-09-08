package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product_group.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/product-groups")
public class ProductGroupController {


    private final ProductGroupService productGroupService;

    /**
     * 특정 회사에 등록된 품목 그룹 리스트 조회 (검색어 포함)
     * @param companyId 회사 ID
     * @param searchTerm 검색어
     * @return 해당 회사의 모든 품목 그룹 리스트를 반환
     */
    @PostMapping("/{company_id}")
    public ResponseEntity<List<ProductGroupDto>> getAllProductGroups(@PathVariable("company_id") Long companyId,
                                                                     @RequestParam(value = "search", required = false) String searchTerm) {

        List<ProductGroupDto> response = productGroupService.findAllProductGroups(companyId, searchTerm);
        return ResponseEntity.ok(response);
    }

    /**
     * 품목 그룹을 등록함
     * @param productGroupDto
     * @return 등록된 품목 그룹의 DTO를 반환
     */
    @PostMapping("/{company_id}/save")
    public ResponseEntity<ProductGroupDto> saveProductGroup(@PathVariable("company_id") Long companyId,
                                                            @RequestBody ProductGroupDto productGroupDto) {

        try {
            return productGroupService.saveProductGroup(companyId, productGroupDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 등록된 품목 그룹을 업데이트함
     * @param id 업데이트하려는 품목 그룹의 id
     * @param productGroupDto 업데이트할 품목 그룹 정보
     * @return 업데이트된 품목 그룹의 DTO를 반환
     */
    @PutMapping("/{company_id}/update/{id}")
    public ResponseEntity<ProductGroupDto> updateProductGroup(@PathVariable("company_id") Long companyId,
                                                              @PathVariable("id") Long id,
                                                              @RequestBody ProductGroupDto productGroupDto) {
        try{
            return productGroupService.updateProduct(companyId, id, productGroupDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 등록된 품목 그룹을 삭제함
     * @param id 삭제할 품목 그룹의 id
     * @return 품목 그룹 삭제 성공 여부를 문자열로 반환
     */
    @DeleteMapping("/{company_id}/delete/{id}")
    public ResponseEntity<String> deleteProductGroup(@PathVariable("company_id") Long companyId,
                                                     @PathVariable("id") Long id) {
        String result = productGroupService.deleteProductGroup(companyId, id);
        return ResponseEntity.ok(result);
    }

    /**
     * ID를 통해 품목 그룹 사용중단.
     *
     * @param id 사용중단할 품목 그룹의 ID
     * @return 사용중단 처리 결과를 담은 응답 엔티티
     */
    @PutMapping("/{company_id}/{id}/deactivate")
    public ResponseEntity<String> deactivateProductGroup(@PathVariable("company_id") Long companyId,
                                                         @PathVariable("id") Long id) {
        String result = productGroupService.deactivateProductGroup(companyId, id);
        return ResponseEntity.ok(result);
    }

    /**
     * ID를 통해 품목 그룹을 재사용
     *
     * @param id 다시 재사용할 품목 그룹의 ID
     * @return 재사용할 처리 결과를 담은 응답 엔티티
     */
    @PutMapping("/{company_id}/{id}/reactivate")
    public ResponseEntity<String> reactivateProductGroup(@PathVariable("company_id") Long companyId,
                                                         @PathVariable("id") Long id) {
        String result = productGroupService.reactivateProductGroup(companyId, id);
        return ResponseEntity.ok(result);
    }


}
