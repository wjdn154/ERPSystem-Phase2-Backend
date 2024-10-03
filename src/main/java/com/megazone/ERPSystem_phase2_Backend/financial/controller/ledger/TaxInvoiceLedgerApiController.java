package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.TaxInvoiceLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaxInvoiceLedgerApiController {
    private final TaxInvoiceLedgerService taxInvoiceLedgerService;


    @PostMapping("/api/financial/ledger/taxInvoice/show")
    public ResponseEntity<Object> show(@RequestBody TaxInvoiceLedgerSearchDTO dto ) {
        Object showResult = taxInvoiceLedgerService.show(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
