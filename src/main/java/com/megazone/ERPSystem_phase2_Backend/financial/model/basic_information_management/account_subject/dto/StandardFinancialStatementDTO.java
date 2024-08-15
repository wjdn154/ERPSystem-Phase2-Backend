package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StandardFinancialStatementDTO {
    String code; // 표준 재무제표 코드
    String name; // 표준 재무제표 이름
}
