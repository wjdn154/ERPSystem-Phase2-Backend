package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;
import lombok.*;

/**
 * 주류 정보 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LiquorDTO {
    private Long id;
    private String code; // 주류 코드
    private String name; // 주류명
}