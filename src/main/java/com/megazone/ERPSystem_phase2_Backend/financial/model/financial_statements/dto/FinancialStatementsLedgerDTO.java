package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialStatementsLedgerDTO {
    private BigDecimal totalDebitBalance;
    private BigDecimal totalDebitAmount;
    private BigDecimal totalCreditBalance;
    private BigDecimal totalCreditAmount;
    private String accountStructureCode;
    private Integer accountStructureMin;
    private Long financialStatementId;
    private String financialStateCategory;
    private String mediumCategory;
    private String smallCategory;
    private String financialStatementsName;
    private String financialStatementsCode;

    public static FinancialStatementsLedgerDTO create(BigDecimal totalDebitBalance, BigDecimal totalDebitAmount,
                                                      BigDecimal totalCreditBalance, BigDecimal totalCreditAmount,
                                                      String accountStructureCode, Integer accountStructureMin,
                                                      Long financialStatementId, String financialStateCategory,
                                                      String mediumCategory, String smallCategory, String financialStatementsName,
                                                      String financialStatementsCode) {
        return new FinancialStatementsLedgerDTO(
                totalDebitBalance,
                totalDebitAmount,
                totalCreditBalance,
                totalCreditAmount,
                accountStructureCode,
                accountStructureMin,
                financialStatementId,
                financialStateCategory,
                mediumCategory,
                smallCategory,
                financialStatementsName,
                financialStatementsCode
        );
    }
}
