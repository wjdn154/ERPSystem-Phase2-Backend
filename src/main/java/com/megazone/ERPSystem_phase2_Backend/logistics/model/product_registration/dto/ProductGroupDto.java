package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductGroupDto {
    private Long id;
    private String code;    // 품목 그룹 코드를 받을 필드
    private String name;    // 품목 그룹명을 받을 필드
    private boolean isActive; // 품목 그룹 활성화 여부를 받을 필드
}
