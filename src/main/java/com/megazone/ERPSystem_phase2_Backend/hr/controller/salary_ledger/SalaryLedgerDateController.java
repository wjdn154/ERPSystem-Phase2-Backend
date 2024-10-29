package com.megazone.ERPSystem_phase2_Backend.hr.controller.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDateShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger.SalaryLedgerDateService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class SalaryLedgerDateController {

    private final SalaryLedgerDateService salaryLedgerDateService;


    @PostMapping("/salaryLedgerDate/show")
    public ResponseEntity<Object> showAll() {
        try{
            List<SalaryLedgerDateShowDTO> result = salaryLedgerDateService.showAll();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
