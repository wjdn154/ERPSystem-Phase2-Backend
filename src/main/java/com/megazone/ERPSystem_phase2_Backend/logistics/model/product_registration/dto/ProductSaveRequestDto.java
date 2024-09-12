package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductType;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaveRequestDto {

    private String code; // 품목 코드
    private String name; // 품목명
    private Long productGroupId;// 폼목 그룹_id
    private Long productionRoutingId; // 생산라우팅_id
    private String standard; // 규격
    private String unit; // 단위
    private Double purchasePrice; // 입고단가
    private Double salesPrice; // 출고 단가
    private ProductType productType; // 품목 구분

    public Product toEntity(ProductGroup productGroup, ProcessRouting processRouting) {
        return Product.builder()
                .code(code)
                .name(name)
                .productGroup(productGroup)
                .processRouting(processRouting)
                .standard(standard)
                .unit(unit)
                .purchasePrice(purchasePrice)
                .salesPrice(salesPrice)
                .productType(productType)
                .build();
    }
}
