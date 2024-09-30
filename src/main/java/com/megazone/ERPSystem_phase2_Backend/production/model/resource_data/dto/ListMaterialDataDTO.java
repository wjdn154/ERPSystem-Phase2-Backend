package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 자재 리스트, 수정, 삭제 dto
 * */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListMaterialDataDTO {

    private Long id;
    private String materialCode;           //자재 코드
    private String materialName;           //자재 이름
    private MaterialType materialType;     //자재유형
    private Long stockQuantity;            //재고 수량
    private BigDecimal purchasePrice;      //구매 가격
    private String representativeCode;     //거래처 코드
    private String representativeName;     //거래처 명 (한 거래처에 여러개의 자재)
    private Long hazardousMaterialQuantity;    //유해물질 개수
}
