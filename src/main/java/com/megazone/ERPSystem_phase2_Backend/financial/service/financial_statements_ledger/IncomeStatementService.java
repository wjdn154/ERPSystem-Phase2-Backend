package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;

import java.util.List;

public interface IncomeStatementService {
    List<IncomeStatementLedgerShowDTO> show(IncomeStatementSearchDTO dto);
}
