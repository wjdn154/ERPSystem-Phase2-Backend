package com.megazone.ERPSystem_phase2_Backend.logistics.controller.product_registration;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
     * 품목 그룹 리스트 조회
     * @return 등록된 모든 품목 그룹을 반환
     */
    @PostMapping
    public ResponseEntity<List<ProductGroupDto>> getAllProductGroups() {
        List<ProductGroupDto> response =  productGroupService.findAllProductGroups();

        return ResponseEntity.ok(response);

    }

    /**
     * 등록된 각 품목 그룹 정보 조회
     * @param id 품목 그룹 ID
     * @return 특정 id에 해당하는 품목 그룹 정보 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<ProductGroupDto> getProductGroupById(@PathVariable("id") Long id) {
        ProductGroupDto response = productGroupService.getProductGroupById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 품목 그룹을 등록함
     * @param productGroupDto
     * @return 등록된 품목 그룹의 DTO를 반환
     */
    @PostMapping("/save")
    public ResponseEntity<ProductGroupDto> saveProductGroup(@RequestBody ProductGroupDto productGroupDto) {
        try {
            return productGroupService.saveProductGroup(productGroupDto)
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
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductGroupDto> updateProductGroup(@PathVariable("id") Long id,
                                                              @RequestBody ProductGroupDto productGroupDto) {
        try{
            return productGroupService.updateProduct(id, productGroupDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductGroup(@PathVariable("id") Long id) {
        String result = productGroupService.deleteProductGroup(id);
        return ResponseEntity.ok(result);
    }



}
