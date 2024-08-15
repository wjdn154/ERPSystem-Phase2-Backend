package com.megazone.ERPSystem_phase2_Backend.production.model.material_requirements_planning.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardBomDTO {

    private String bomCode;       // BOM 코드
    private String bomName;               // BOM 이름
    private Double lossRate;              // BOM 전체 손실율
    private List<BomMaterialDTO> materials; // 자재 목록
}
