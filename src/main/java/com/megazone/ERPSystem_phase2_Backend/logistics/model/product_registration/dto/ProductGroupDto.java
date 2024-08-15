package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroupDto {
    private String code;    // 품목 그룹 코드를 받을 필드
    private String name;    // 품목 그룹명을 받을 필드

    public ProductGroup toEntity() {
        return new ProductGroup(null, code, name);
    }
}
