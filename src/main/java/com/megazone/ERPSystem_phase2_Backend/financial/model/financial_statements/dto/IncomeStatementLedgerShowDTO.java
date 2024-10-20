package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.IncomeStateNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeStatementLedgerShowDTO {
    private String level;
    private String name;
    private BigDecimal totalAmount;

    public static IncomeStatementLedgerShowDTO create(IncomeStateNode node, String level) {
        return new IncomeStatementLedgerShowDTO(
                level,
                node.getName(),
                node.getTotalAmount()
        );
    }
}
