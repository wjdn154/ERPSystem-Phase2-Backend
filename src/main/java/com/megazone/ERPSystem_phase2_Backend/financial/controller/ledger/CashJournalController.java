package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowAllListDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.CashJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 현금출납장
 */
@RestController
@RequiredArgsConstructor
public class CashJournalController {
    public final CashJournalService cashJournalService;

    @PostMapping("/api/financial/ledger/cashJournal/show")
    public ResponseEntity<Object> show(@RequestBody CashJournalSearchDTO dto) {
        CashJournalShowAllListDTO requestDTO = cashJournalService.showAll(dto);

        return requestDTO != null ? ResponseEntity.status(HttpStatus.OK).body(requestDTO) :
        ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 조건에 거래가 없습니다.");
    }
}
