package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateKindDTO {
    private Long id; // 고유 식별자
    private String code; // 법인종류 코드
    private String kind; // 법인종류
    private String description; // 법인종류 설명
}
