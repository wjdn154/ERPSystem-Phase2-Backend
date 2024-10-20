package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;

public interface FinancialStatementsLedgerService {
    Object show(FinancialStatementsLedgerSearchDTO dto);
}
