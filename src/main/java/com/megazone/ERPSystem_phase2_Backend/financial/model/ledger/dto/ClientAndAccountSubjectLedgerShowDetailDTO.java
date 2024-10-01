package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAndAccountSubjectLedgerShowDetailDTO {
    private String accountSubjectCode;
    private String accountSubjectName;
    private BigDecimal previousTotalCash;
    private BigDecimal totalDebitAmount;
    private BigDecimal totalCreditAmount;
    private BigDecimal totalCashAmount;

    public static ClientAndAccountSubjectLedgerShowDetailDTO create(String accountSubjectCode, String accountSubjectName,
                                                                    BigDecimal previousTotalCash, BigDecimal totalDebitAmount,
                                                                    BigDecimal totalCreditAmount, BigDecimal totalCashAmount) {
        return new ClientAndAccountSubjectLedgerShowDetailDTO(
                accountSubjectCode,
                accountSubjectName,
                previousTotalCash,
                totalDebitAmount,
                totalCreditAmount,
                totalCashAmount
        );
    }
}
