package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashMemoDTO {
    private Long id; // 현금 적요 ID
    private String content; // 현금 적요 내용
}
