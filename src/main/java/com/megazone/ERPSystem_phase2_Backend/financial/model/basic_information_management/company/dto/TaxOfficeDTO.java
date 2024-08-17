package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxOfficeDTO {
    private Long id; // 고유식별자
    private String code; // 세무서 코드
    private String region; // 세무서 지역
}
