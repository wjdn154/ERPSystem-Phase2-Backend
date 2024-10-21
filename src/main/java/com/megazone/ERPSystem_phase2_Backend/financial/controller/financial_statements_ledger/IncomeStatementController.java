package com.megazone.ERPSystem_phase2_Backend.financial.controller.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger.IncomeStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IncomeStatementController {
    private final IncomeStatementService incomeStatementService;

    @PostMapping("/api/financial/ledger/incomeStatement/show")
    public ResponseEntity<Object> show(@RequestBody IncomeStatementSearchDTO dto) {
        Object result = incomeStatementService.show(dto);
        return null;
    }
}
