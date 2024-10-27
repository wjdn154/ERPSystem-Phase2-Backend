package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeStatementSearchDTO {
    private YearMonth yearMonth;

    public static IncomeStatementSearchDTO create(YearMonth yearMonth) {
        return new IncomeStatementSearchDTO(
                yearMonth
        );
    }
}
