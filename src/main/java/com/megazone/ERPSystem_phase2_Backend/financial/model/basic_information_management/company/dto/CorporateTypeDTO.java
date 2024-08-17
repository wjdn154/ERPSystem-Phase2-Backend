package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateTypeDTO {
    private Long id; // 고유 식별자
    private String code; // 법인구분 코드
    private String type; // 법인구분
    private String description; // 법인구분 설명
}
