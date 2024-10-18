package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id; // 고유 식별자
    private String code; // 품목 코드
    private String name; // 품목명
    private Long clientId; // 거래처 ID
    private String clientName; // 거래처명
    private Long productGroupId; // 품목 그룹 ID
    private String productGroupCode; // 품목 그룹 코드
    private String productGroupName; // 폼목 그룹명
    private String standard; // 규격
    private String unit; // 단위
    private BigDecimal purchasePrice; // 입고단가
    private BigDecimal salesPrice; // 출고 단가
    private ProductType productType; // 품목 구분
    private Long processRoutingId; // 생산 라우팅 ID
    private String processRoutingCode; // 생산라우팅 코드
    private String processRoutingName; // 생산라우팅명
    private String imagePath; // 이미지 경로
    private String remarks; // 적요
    private Boolean isActive; // 품목 사용 여부

}
