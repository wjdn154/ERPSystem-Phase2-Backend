package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BomMaterialDTO {

    private Long id;
    private Long bomId;
    private String BomCode;
    private Double version; // BOM 버전

    private Long productId;           // 품목 ID
//    private String productCode;       // 품목 코드
//    private String productName;       // 품목명
//    private String productCategory;   // 품목군 (카테고리)

    private Long materialId;          // 자재 ID
    private String materialName;      // 자재명

    @PositiveOrZero
    private Long quantity;      // 자재 필요 수량
    @PositiveOrZero
    private Double lossRate;          // 자재 손실율

    private String unitOfMeasure;         // 자재의 측정 단위 (Kg, Gallon 등)
    private Long substituteMaterialId;    // 대체 자재 ID (옵션)
    private String substituteMaterialName;// 대체 자재명 (옵션)

//    private MaterialStatus materialStatus;        // 자재 상태 (사용, 미사용, 단종)
}
