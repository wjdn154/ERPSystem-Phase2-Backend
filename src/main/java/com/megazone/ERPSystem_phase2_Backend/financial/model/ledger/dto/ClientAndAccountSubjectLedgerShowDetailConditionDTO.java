package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAndAccountSubjectLedgerShowDetailConditionDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String startAccountSubjectCode;
    private String endAccountSubjectCode;
    private Long clientId;
}
