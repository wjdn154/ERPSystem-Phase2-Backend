package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 자재 등록 dto
 * 품목 : 등록되어 있는 품목의 이름들을 checkbox로 선택하여 등록
 * 유해물질 : 등록되어 있는 유해물질의 이름들을 checkbox로 선택하여 등록
 * */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialDataShowDTO {

    private Long id;
    private String materialCode;           //자재 코드
    private String materialName;           //자재 이름
    private MaterialType materialType;     //자재유형
    private Long stockQuantity;            //재고 수량
    private BigDecimal purchasePrice;      //구매 가격
    private String representativeCode;     //거래처 코드
    private String representativeName;     //거래처 명 (한 거래처에 여러개의 자재)

//    private List<ProductMaterialDTO> product;             //품목 리스트(한 자재에 여러개의 품목?)
    private List<HazardousMaterialDTO> hazardousMaterial;   //유해물질 리스트
}
