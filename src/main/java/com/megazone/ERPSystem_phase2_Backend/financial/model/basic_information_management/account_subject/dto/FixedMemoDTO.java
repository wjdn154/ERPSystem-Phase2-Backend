package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FixedMemoDTO {
    private Long id; // 고정 적요 ID
    private String content; // 고정 적요 내용
    private String category; // 고정 적요 카테고리
}
