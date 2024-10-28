package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferTypeResponseDTO {
    private Long id;            // 자동 생성된 ID
    private String code;        // 발령 유형 코드
    private String description; // 발령 유형 설명
}
