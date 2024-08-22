package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductType;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaveResponseDto {

    private String code; // 품목 코드
    private String name; // 품목명
    private String productGroupName;// 폼목 그룹_id
    private String standard; // 규격
    private String unit; // 단위
    private Double purchasePrice; // 입고단가
    private Double salesPrice; // 출고 단가
    private ProductType productType; // 품목 구분
    private ProductionRouting productionRouting; // 생산라우팅

    public Product toEntity(ProductGroup productGroup) {
        return Product.builder()
                .code(code)
                .name(name)
                .productGroup(productGroup)
                .standard(standard)
                .unit(unit)
                .purchasePrice(purchasePrice)
                .salesPrice(salesPrice)
                .productType(productType)
                .productionRouting(productionRouting)
                .build();
    }
}
