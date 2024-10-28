package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerSearchDTO;

import java.util.List;

public interface SalaryLedgerRepositoryCustom {
    List<SalaryLedgerDTO> findLedger(SalaryLedgerSearchDTO dto);
}
