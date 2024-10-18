package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialStatementsLedgerShowDTO {
    private BigDecimal totalDebitBalance;
    private BigDecimal totalDebitAmount;
    private BigDecimal totalCreditBalance;
    private BigDecimal totalCreditAmount;
    private Long financialStatementId;
    private String mediumCategory;
    private String smallCategory;
    private String financialStatementsName;
    private String financialStatementsCode;

    public static FinancialStatementsLedgerShowDTO create( BigDecimal totalDebitBalance, BigDecimal totalDebitAmount,
                                                           BigDecimal totalCreditBalance, BigDecimal totalCreditAmount,
                                                          Long financialStatementId, String mediumCategory, String smallCategory,
                                                           String financialStatementsName,
                                                          String financialStatementsCode) {
        return new FinancialStatementsLedgerShowDTO(
                totalDebitBalance,
                totalDebitAmount,
                totalCreditBalance,
                totalCreditAmount,
                financialStatementId,
                mediumCategory,
                smallCategory,
                financialStatementsName,
                financialStatementsCode
        );
    }
}
