package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountLedgerShowAllDTO {
    private List<AccountLedgerShowDTO> accountLedgerShows;
    private BigDecimal monthlyTotalDebitAmount;
    private BigDecimal monthlyTotalCreditAmount;
    private BigDecimal monthlyTotalCashAmount;
    private BigDecimal cumulativeTotalDebitAmount;
    private BigDecimal cumulativeTotalCreditAmount;
    private BigDecimal cumulativeTotalCashAmount;

    public static AccountLedgerShowAllDTO create(List<AccountLedgerShowDTO> accountLedgerShows,
                                               BigDecimal monthlyTotalDebitAmount,
                                               BigDecimal monthlyTotalCreditAmount,
                                               BigDecimal monthlyTotalCashAmount,
                                               BigDecimal cumulativeTotalDebitAmount,
                                               BigDecimal cumulativeTotalCreditAmount,
                                               BigDecimal cumulativeTotalCashAmount) {
        return new AccountLedgerShowAllDTO(
                accountLedgerShows,
                monthlyTotalDebitAmount,
                monthlyTotalCreditAmount,
                monthlyTotalCashAmount,
                cumulativeTotalDebitAmount,
                cumulativeTotalCreditAmount,
                cumulativeTotalCashAmount
        );
    }
}
