package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GeneralShowDTO {
    private String accountSubjectCode;
    private String accountSubjectName;
    private Month month;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal totalCash;

    public static GeneralShowDTO create(String accountSubjectCode, String accountSubjectName, Month month,
                                        BigDecimal totalDebit, BigDecimal totalCredit, BigDecimal totalCash) {
        return new GeneralShowDTO(accountSubjectCode, accountSubjectName, month, totalDebit, totalCredit, totalCash);
    }

}
