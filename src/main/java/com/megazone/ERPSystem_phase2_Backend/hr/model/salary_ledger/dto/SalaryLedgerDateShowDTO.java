package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedgerDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryLedgerDateShowDTO {
    private Long Id;
    private String code;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public static SalaryLedgerDateShowDTO create(SalaryLedgerDate salaryLedgerDate) {
        return new SalaryLedgerDateShowDTO(
                salaryLedgerDate.getId(),
                salaryLedgerDate.getCode(),
                salaryLedgerDate.getDescription(),
                salaryLedgerDate.getStartDate(),
                salaryLedgerDate.getEndDate()
        );
    }
}