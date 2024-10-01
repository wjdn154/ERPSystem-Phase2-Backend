package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountLedgerSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String startAccountCode;
    private String endAccountCode;
}
