package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StructureDTO {
    private String code; // 구조 코드
    private String name; // 구조 이름
    private int min; // 최소값
    private int max; // 최대값
}