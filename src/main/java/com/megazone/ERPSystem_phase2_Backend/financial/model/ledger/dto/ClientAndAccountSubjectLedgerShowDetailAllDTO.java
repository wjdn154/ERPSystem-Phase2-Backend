package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAndAccountSubjectLedgerShowDetailAllDTO {
    private List<ClientAndAccountSubjectLedgerShowDetailDTO> detailList;
    private BigDecimal totalSumPreviousCash;
    private BigDecimal totalSumDebitAmount;
    private BigDecimal totalSumCreditAmount;
    private BigDecimal totalSumCashAmount;


    public static ClientAndAccountSubjectLedgerShowDetailAllDTO create(List<ClientAndAccountSubjectLedgerShowDetailDTO> detailList,
                                                                       BigDecimal totalSumPreviousCash, BigDecimal totalSumDebitAmount,
                                                                       BigDecimal totalSumCreditAmount, BigDecimal totalSumCashAmount) {
        return new ClientAndAccountSubjectLedgerShowDetailAllDTO(
                detailList,
                totalSumPreviousCash,
                totalSumDebitAmount,
                totalSumCreditAmount,
                totalSumCashAmount
        );
    }
}
