package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashJournalShowAllListDTO {
    private BigDecimal previousTotalDepositAmount;
    private BigDecimal previousTotalWithdrawalAmount;
    private BigDecimal previousTotalCashAmount;
    private List<CashJournalShowAllDTO> cashJournalShowAllDTOList;

    public static CashJournalShowAllListDTO create(BigDecimal previousTotalDepositAmount, BigDecimal previousTotalWithdrawalAmount,
                                                   BigDecimal previousTotalCashAmount, List<CashJournalShowAllDTO> cashJournalShowAllDTOList) {
        return new CashJournalShowAllListDTO(
                previousTotalDepositAmount,
                previousTotalWithdrawalAmount,
                previousTotalCashAmount,
                cashJournalShowAllDTOList
        );
    }
}
