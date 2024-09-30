package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountLedgerShowAllListDTO {
    private BigDecimal previousTotalDebitAmount;
    private BigDecimal previousTotalCreditAmount;
    private BigDecimal previousTotalCashAmount;
    private List<AccountLedgerShowAllDTO> cashJournalShowAllDTOList;

    public static AccountLedgerShowAllListDTO create(BigDecimal previousTotalDepositAmount, BigDecimal previousTotalWithdrawalAmount,
                                                   BigDecimal previousTotalCashAmount, List<AccountLedgerShowAllDTO> cashJournalShowAllDTOList) {
        return new AccountLedgerShowAllListDTO(
                previousTotalDepositAmount,
                previousTotalWithdrawalAmount,
                previousTotalCashAmount,
                cashJournalShowAllDTOList
        );
    }
}
