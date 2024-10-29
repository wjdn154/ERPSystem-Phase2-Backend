package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;

import java.util.List;

public interface SalaryAllowanceService {
    List<SalaryLedgerAllowanceShowDTO> findSalaryAllowances(Long salaryId);
}
