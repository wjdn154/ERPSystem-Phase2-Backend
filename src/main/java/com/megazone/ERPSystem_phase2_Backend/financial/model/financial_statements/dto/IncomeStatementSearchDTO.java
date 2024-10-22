package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeStatementSearchDTO {
    private LocalDate searchDate;
}
