package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListMaterialDataDTO {

    private Long id;
    private String materialCode;           //자재 코드
    private String materialName;           //자재 이름
    private String materialType;           //자재유형
    private Long stockQuantity;            //재고 수량
    private BigDecimal purchasePrice;      //구매 가격
    private String representativeName;     //거래처 명 (한 거래처에 여러개의 자재)
    private String productName;             //품목 이름(한 품목에 여러개의 자재? 한 자재에 여러개의 품목?)
    private List<HazardousMaterialDTO> hazardousMaterial;   //유해물질 리스트
}
