package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeStatementLedgerDTO {
    private BigDecimal amount;
    private String accountStructureCode;
    private Integer accountStructureMin;
    private String mediumCategory;
    private String smallCategory;
    private Long financialStatementId;
    private String financialStatementsName;
    private String financialStatementsCode;

    public static IncomeStatementLedgerDTO create(BigDecimal amount,
                                                  String accountStructureCode, Integer accountStructureMin,
                                                  Long financialStatementId, String mediumCategory, String smallCategory,
                                                  String financialStatementsName,
                                                  String financialStatementsCode) {
        return new IncomeStatementLedgerDTO(
                amount,
                accountStructureCode,
                accountStructureMin,
                mediumCategory,
                smallCategory,
                financialStatementId,
                financialStatementsName,
                financialStatementsCode
        );
    }
}
