package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDateShowDTO;

import java.util.List;

public interface SalaryLedgerDateService {
    List<SalaryLedgerDateShowDTO> showAll();
}
