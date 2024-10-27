package com.megazone.ERPSystem_phase2_Backend.financial.controller.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerDashBoardDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger.IncomeStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IncomeStatementController {
    private final IncomeStatementService incomeStatementService;

    @PostMapping("/api/financial/ledger/incomeStatement/show")
    public ResponseEntity<Object> show(@RequestBody IncomeStatementSearchDTO dto) {
        List<IncomeStatementLedgerShowDTO> result = incomeStatementService.show(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    @PostMapping("/api/financial/ledger/incomeStatement/test")
//    public ResponseEntity<Object> test() {
//        IncomeStatementLedgerDashBoardDTO result = incomeStatementService.DashBoardShow();
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }
}
