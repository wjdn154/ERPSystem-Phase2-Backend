package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.enums.DailyAndMonthType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyAndMonthJournalSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private DailyAndMonthType journalType;
}
