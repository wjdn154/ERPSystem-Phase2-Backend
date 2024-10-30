package com.megazone.ERPSystem_phase2_Backend.hr.controller.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger.IncomeTaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IncomeTaxController {
    private final IncomeTaxService incomeTaxService;

    @PostMapping()
    public ResponseEntity<Object> show() {
        // incomeTaxService
        return ResponseEntity.ok().build();
    }
}
