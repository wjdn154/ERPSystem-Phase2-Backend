package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomMaterialDTO {

    private Long productId;           // 품목 ID
    private String productCode;       // 품목 코드
    private String productName;       // 품목명
    private String productCategory;   // 품목군 (카테고리)
    private Long materialId;          // 자재 ID
    private String materialName;      // 자재명
    private BigDecimal quantity;      // 자재 필요 수량
    private Double lossRate;          // 자재 손실율
    private Long substituteMaterialId; // 대체 자재 ID (옵션)
}
