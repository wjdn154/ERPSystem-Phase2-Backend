package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.CustomNode.CustomNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalShowDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialStatementsLedgerShowDTO {
    private String level;
    private String name;
    private BigDecimal totalDebitBalance;
    private BigDecimal totalDebitAmount;
    private BigDecimal totalCreditBalance;
    private BigDecimal totalCreditAmount;

    public static FinancialStatementsLedgerShowDTO create(FinancialStateNode node, String level) {

        return new FinancialStatementsLedgerShowDTO(
                level,
                node.getName(),
                node.getTotalDebitBalance(),
                node.getTotalDebitAmount(),
                node.getTotalCreditBalance(),
                node.getTotalCreditAmount()
        );
    }

    public static FinancialStatementsLedgerShowDTO create(String level, String name,BigDecimal totalDebitBalance,
                                                          BigDecimal totalDebitAmount,BigDecimal totalCreditBalance,
                                                          BigDecimal totalCreditAmount) {
        return new FinancialStatementsLedgerShowDTO(
                level,
                name,
                totalDebitBalance,
                totalDebitAmount,
                totalCreditBalance,
                totalCreditAmount
        );
    }
}
