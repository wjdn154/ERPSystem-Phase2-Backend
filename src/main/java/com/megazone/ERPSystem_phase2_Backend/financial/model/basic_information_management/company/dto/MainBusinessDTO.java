package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainBusinessDTO {
    private Long id; // 고유 식별자
    private String code; // 업종코드
    private String businessType; // 업태
    private String item; // 종목
}
