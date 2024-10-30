package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryLedgerSearchDTO {
    private Long employeeId;
    private Long salaryLedgerDateId;
}
