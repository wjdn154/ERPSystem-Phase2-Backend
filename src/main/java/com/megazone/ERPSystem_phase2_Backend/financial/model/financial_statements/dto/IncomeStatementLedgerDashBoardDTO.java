package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeStatementLedgerDashBoardDTO {
    private List<List<IncomeStatementLedgerShowDTO>> incomeStatementLedger;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;

    public static IncomeStatementLedgerDashBoardDTO create(List<List<IncomeStatementLedgerShowDTO>> result, BigDecimal totalIncome, BigDecimal totalExpense) {
        return new IncomeStatementLedgerDashBoardDTO(
                result,
                totalIncome,
                totalExpense
        );
    }
}
