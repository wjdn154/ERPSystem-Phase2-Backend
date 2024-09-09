package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id; // 고유 식별자
    private String code; // 품목 코드
    private String name; // 품목명
    private String productGroupCode; // 품목 그룹 코드
    private String productGroupName; // 폼목 그룹
    private String standard; // 규격
    private String unit; // 단위
    private Double purchasePrice; // 입고단가
    private Double salesPrice; // 출고 단가
    private ProductType productType; // 품목 구분
    private String productionRoutingCode; // 생산라우팅 코드
    private String productionRoutingName; // 생산라우팅
    private Boolean isActive; // 품목 사용 여부

}
