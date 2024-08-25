package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedMemoDTO {
    private Long id; // 고정 적요 ID
    private String code; // 고정 적요 코드
    private String content; // 고정 적요 내용
    private String category; // 고정 적요 카테고리

    public FixedMemoDTO(String code, String content, String category) {
        this.code = code;
        this.content = content;
        this.category = category;
    }
}
