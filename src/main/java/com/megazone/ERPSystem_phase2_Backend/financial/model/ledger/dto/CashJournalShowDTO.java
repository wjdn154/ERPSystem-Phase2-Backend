package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashJournalShowDTO {
    private LocalDate voucherDate;
    private String transactionDescription;
    private String clientCode;
    private String clientName;
    private BigDecimal depositAmount;
    private BigDecimal withdrawalAmount;
    private BigDecimal cashAmount;

    public static CashJournalShowDTO create(LocalDate voucherDate, String transactionDescription, String clientCode,
                                            String clientName, BigDecimal depositAmount, BigDecimal withdrawalAmount, BigDecimal cashAmount) {
        return new CashJournalShowDTO(
                voucherDate,
                transactionDescription,
                clientCode,
                clientName,
                depositAmount,
                withdrawalAmount,
                cashAmount
        );
    }
}
