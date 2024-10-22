package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerShowDTO;

import java.util.List;

public interface FinancialStatementsLedgerService {
    List<FinancialStatementsLedgerShowDTO> show(FinancialStatementsLedgerSearchDTO dto);
}
