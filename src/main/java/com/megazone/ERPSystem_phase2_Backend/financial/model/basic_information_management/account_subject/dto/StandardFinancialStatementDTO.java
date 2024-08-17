package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StandardFinancialStatementDTO {
    private String code; // 표준 재무제표 코드
    private String name; // 표준 재무제표 이름
}
