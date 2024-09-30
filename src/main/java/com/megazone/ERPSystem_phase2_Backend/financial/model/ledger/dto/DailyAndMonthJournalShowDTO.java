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
    private String accountCode;
    private String accountName;
    private String accountStructureCode;
    private String accountStructureMediumCategory;
    private String accountStructureSmallCategory;
    private BigDecimal cashTotalDebit;
    private BigDecimal subTotalDebit;
    private BigDecimal sumTotalDebit;
    private BigDecimal cashTotalCredit;
    private BigDecimal subTotalCredit;
    private BigDecimal sumTotalCredit;

    public static DailyAndMonthJournalShowDTO create(String accountCode, String accountName, String accountStructureCode,
                                                     String accountStructureMediumCategory,
                                                     String accountStructureSmallCategory, BigDecimal cashTotalDebit,
                                                     BigDecimal subTotalDebit, BigDecimal sumTotalDebit, BigDecimal cashTotalCredit,
                                                     BigDecimal subTotalCredit, BigDecimal sumTotalCredit) {
        return new DailyAndMonthJournalShowDTO(
                accountCode,
                accountName,
                accountStructureCode,
                accountStructureMediumCategory,
                accountStructureSmallCategory,
                cashTotalDebit,
                subTotalDebit,
                sumTotalDebit,
                cashTotalCredit,
                subTotalCredit,
                sumTotalCredit
        );
    }
}
