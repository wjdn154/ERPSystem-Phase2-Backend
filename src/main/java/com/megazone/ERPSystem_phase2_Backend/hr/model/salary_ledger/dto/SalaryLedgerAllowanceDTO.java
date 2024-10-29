package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryLedgerAllowanceDTO {
    private Long id;
    private String name;
    private BigDecimal amount;
}