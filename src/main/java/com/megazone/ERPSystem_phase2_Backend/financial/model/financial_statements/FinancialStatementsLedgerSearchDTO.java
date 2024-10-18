package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FinancialStatementsLedgerSearchDTO {
    private LocalDate searchDate;
}
