package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import lombok.*;

/**
 * 사업 정보(업태 및 종목) DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInfoDTO {
    private String businessType; // 업태
    private String businessItem; // 종목
}