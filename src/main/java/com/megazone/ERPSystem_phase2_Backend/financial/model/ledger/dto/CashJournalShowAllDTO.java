package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashJournalShowAllDTO {
    private List<CashJournalShowDTO> cashJournalShows;
    private BigDecimal monthlyTotalDepositAmount;
    private BigDecimal monthlyTotalWithdrawalAmount;
    private BigDecimal monthlyTotalCashAmount;
    private BigDecimal cumulativeTotalDepositAmount;
    private BigDecimal cumulativeTotalWithdrawalAmount;
    private BigDecimal cumulativeTotalCashAmount;

    public static CashJournalShowAllDTO create(List<CashJournalShowDTO> cashJournalShows,
                                               BigDecimal cumulativeTotalDepositAmount,BigDecimal cumulativeTotalWithdrawalAmount,
                                               BigDecimal cumulativeTotalCashAmount) {

        BigDecimal monthlyTotalDepositAmount = BigDecimal.ZERO;
        BigDecimal monthlyTotalWithdrawalAmount = BigDecimal.ZERO;
        BigDecimal monthlyTotalCashAmount = BigDecimal.ZERO;


        return new CashJournalShowAllDTO(
                cashJournalShows,
                monthlyTotalDepositAmount,
                monthlyTotalWithdrawalAmount,
                monthlyTotalCashAmount,
                cumulativeTotalDepositAmount,
                cumulativeTotalWithdrawalAmount,
                cumulativeTotalCashAmount
        );
    }
}
