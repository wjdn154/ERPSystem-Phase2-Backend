package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductType;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDto {

    private Long id;
    private String code; // 품목 코드
    private String name; // 품목명
    private String productGroupCode; // 품목 그룹 코드
    private String productGroupName; // 폼목 그룹
    private String standard; // 규격
    private String unit; // 단위
    private BigDecimal purchasePrice; // 입고단가
    private BigDecimal salesPrice; // 출고 단가
    private ProductType productType; // 품목 구분
    private String processRoutingCode; // 생산라우팅 코드
    private String processRoutingName; // 생산라우팅


    public static ProductDetailDto createProductDetailDto(Product product) {
        return new ProductDetailDto(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getProductGroup().getCode(),
                product.getProductGroup().getName(),
                product.getStandard(),
                product.getUnit(),
                product.getPurchasePrice(),
                product.getSalesPrice(),
                product.getProductType(),
                product.getProcessRouting().getCode(),
                product.getProcessRouting().getName()
        );
    }
}
