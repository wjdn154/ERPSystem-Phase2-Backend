package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashMemoDTO {
    private Long id; // 현금 적요 ID
    private String code; // 현금 적요 코드
    private String content; // 현금 적요 내용

    public CashMemoDTO(String code, String content) {
        this.code = code;
        this.content = content;
    }
}



