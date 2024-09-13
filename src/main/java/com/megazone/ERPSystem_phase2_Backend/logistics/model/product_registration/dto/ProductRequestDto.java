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
public class ProductRequestDto {

    private String code; // 품목 코드
    private String name; // 품목명
    private Long clientId; // 거래처_id
    private Long productGroupId;// 폼목 그룹_id
    private Long productionRoutingId; // 생산라우팅_id
    private String standard; // 규격
    private String unit; // 단위
    private Double purchasePrice; // 입고단가
    private Double salesPrice; // 출고 단가
    private ProductType productType; // 품목 구분
    private String imageUrl; // 이미지 경로
    private String remarks; // 적요

}
