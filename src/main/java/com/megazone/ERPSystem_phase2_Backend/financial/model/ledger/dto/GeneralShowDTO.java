package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GeneralShowDTO {
    private LocalDate date;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal totalCash;

    public static GeneralShowDTO create(LocalDate date, BigDecimal totalDebit, BigDecimal totalCredit, BigDecimal totalCash) {
        return new GeneralShowDTO(
                date,
                totalDebit,
                totalCredit,
                totalCash
        );
    }
}
