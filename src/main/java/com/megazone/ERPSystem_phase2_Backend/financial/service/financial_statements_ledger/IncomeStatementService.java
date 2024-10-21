package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;

public interface IncomeStatementService {
    Object show(IncomeStatementSearchDTO dto);
}
