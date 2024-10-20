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
        BigDecimal totalDebitBalance = BigDecimal.ZERO;
        BigDecimal totalCreditBalance = BigDecimal.ZERO;

        if(node.getTotalDebitAmount().subtract(node.getTotalCreditAmount()).compareTo(BigDecimal.ZERO) > 0) {
            totalDebitBalance = node.getTotalDebitAmount().subtract(node.getTotalCreditAmount());
        }
        else if(node.getTotalDebitAmount().subtract(node.getTotalCreditAmount()).compareTo(BigDecimal.ZERO) < 0) {
            totalCreditBalance = node.getTotalCreditAmount().subtract(node.getTotalDebitAmount());
        }

        return new FinancialStatementsLedgerShowDTO(
                level,
                node.getName(),
                totalDebitBalance,
                node.getTotalDebitAmount(),
                totalCreditBalance,
                node.getTotalCreditAmount()
        );
    }
}
