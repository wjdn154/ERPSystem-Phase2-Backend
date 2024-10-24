package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;

@Data
public class IncomeStatementSearchDTO {
    private YearMonth yearMonth;
}
