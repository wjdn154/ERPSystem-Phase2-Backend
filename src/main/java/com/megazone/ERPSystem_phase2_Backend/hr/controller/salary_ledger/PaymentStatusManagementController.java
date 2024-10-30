package com.megazone.ERPSystem_phase2_Backend.hr.controller.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.PaymentStatusManagementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.PaymentStatusManagementShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger.SalaryLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class PaymentStatusManagementController {
    private final SalaryLedgerService salaryLedgerService;


    @PostMapping("/paymentstatusmanagement/show")
    public ResponseEntity<Object> show(@RequestBody PaymentStatusManagementSearchDTO dto) {
        try{
            List<PaymentStatusManagementShowDTO> result = salaryLedgerService.showPaymentStatusManagement(dto);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
