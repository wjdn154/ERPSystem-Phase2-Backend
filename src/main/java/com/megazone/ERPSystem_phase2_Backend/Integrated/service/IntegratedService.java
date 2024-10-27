package com.megazone.ERPSystem_phase2_Backend.Integrated.service;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dto.DashboardDataDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerShowDTO;

import java.util.List;

public interface IntegratedService {
    DashboardDataDTO dashboard();
}
