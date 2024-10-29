package com.megazone.ERPSystem_phase2_Backend.hr.controller.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.FinalizedDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDTO;
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

import java.util.List;
import java.util.Map;

/**
 * 급여계산 및 입력
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class SalaryLedgerController {

    private final SalaryLedgerService salaryLedgerService;

    @PostMapping("/salaryledger/show")
    public ResponseEntity<Object> show(@RequestBody SalaryLedgerSearchDTO dto) {
        try{
            SalaryLedgerDTO result = salaryLedgerService.showSalaryLedger(dto);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/salaryledger/update")
    public ResponseEntity<Object> update(@RequestBody SalaryLedgerDTO dto) {
        try{
            SalaryLedgerDTO result = salaryLedgerService.updateSalaryLedger(dto);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/salaryledger/calculation")
    public ResponseEntity<Object> calculate(@RequestBody Map<String,Long> requestId) {
        try {
            Long salaryLedgerId = requestId.get("salaryLedgerId");
            SalaryLedgerDTO result = salaryLedgerService.salaryLedgerCalculator(salaryLedgerId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/salaryledger/deadlineon")
    public ResponseEntity<Object> deadlineOn(@RequestBody Map<String,Long> requestId) {
        try {
            Long salaryLedgerId = requestId.get("salaryLedgerId");
            FinalizedDTO result = salaryLedgerService.salaryLedgerDeadLineOn(salaryLedgerId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/salaryledger/deadlineoff")
    public ResponseEntity<Object> deadlineOff(@RequestBody Map<String,Long> requestId) {
        try {
            Long salaryLedgerId = requestId.get("salaryLedgerId");
            FinalizedDTO result = salaryLedgerService.salaryLedgerDeadLineOff(salaryLedgerId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
