package com.megazone.ERPSystem_phase2_Backend.financial.controller.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger.FinancialStatementsLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FinancialStatementsLedgerController {

    private final FinancialStatementsLedgerService financialStatementsLedgerService;


    @PostMapping("/api/financial/ledger/financialStatements/show")
    public ResponseEntity<Object> show(@RequestBody FinancialStatementsLedgerSearchDTO dto) {
        List<FinancialStatementsLedgerShowDTO> result = financialStatementsLedgerService.show(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
