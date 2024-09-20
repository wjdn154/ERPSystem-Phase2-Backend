package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientLedgerShowAllDTO {
    private List<ClientLedgerShowDTO> clientLedgerShowAllDTO;
    private BigDecimal totalSumPreviousCash;
    private BigDecimal totalSumDebitAmount;
    private BigDecimal totalSumCreditAmount;
    private BigDecimal totalSumTotalCashAmount;

    public static ClientLedgerShowAllDTO create(List<ClientLedgerShowDTO> clientLedgerShowDTOS,BigDecimal totalSumPreviousCash,
                                                                  BigDecimal totalSumDebitAmount,BigDecimal totalSumCreditAmount,BigDecimal totalSumTotalCashAmount) {
        return new ClientLedgerShowAllDTO(clientLedgerShowDTOS,
                totalSumPreviousCash,
                totalSumDebitAmount,
                totalSumCreditAmount,
                totalSumTotalCashAmount);
    }
}
