package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyAndMonthJournalShowDTO {
    private BigDecimal totalDebitAmount;
    private BigDecimal totalSubDebitAmount;
    private BigDecimal totalDebitCashAmount;
    private BigDecimal totalSubCreditCashAmount;
    private BigDecimal totalCreditAmount;
    private BigDecimal totalCreditCashAmount;
    private String accountCode;
    private String accountName;
}
