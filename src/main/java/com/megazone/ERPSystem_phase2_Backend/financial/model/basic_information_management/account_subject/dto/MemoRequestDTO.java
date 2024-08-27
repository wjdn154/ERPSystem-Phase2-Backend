package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoRequestDTO {
    private String code;     // 적요 코드
    private String memoType; // 적요 구분 (현금적요, 대체적요, 고정적요)
    private String content;  // 적요 내용
}
