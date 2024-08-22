package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    private String code; // 품목 코드
    private String name; // 품목명
    private String productGroupName;// 폼목 그룹
    private String standard; // 규격
    private String unit; // 단위
    private Double purchasePrice; // 입고단가
    private Double salesPrice; // 출고 단가
    private ProductType productType; // 품목 구분
    private Long productionProcessId; // 생산 공정

    public static ProductDetailDto createProductDetailDto(Product product) {
        return new ProductDetailDto(
                product.getCode(),
                product.getName(),
                product.getProductGroup().getName(),
                product.getStandard(),
                product.getUnit(),
                product.getPurchasePrice(),
                product.getSalesPrice(),
                product.getProductType(),
                product.getProductionProcessId()
        );
    }
}