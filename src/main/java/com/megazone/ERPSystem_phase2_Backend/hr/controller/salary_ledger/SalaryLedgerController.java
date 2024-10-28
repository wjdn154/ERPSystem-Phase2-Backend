package com.megazone.ERPSystem_phase2_Backend.hr.controller.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger.SalaryLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class SalaryLedgerController {

    private final SalaryLedgerService salaryLedgerService;

    @PostMapping("/salaryledger/show")
    public ResponseEntity<Object> show(@RequestBody SalaryLedgerSearchDTO dto) {
        try{
//            Object result = salaryLedgerService.showSalaryLedger(dto);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
