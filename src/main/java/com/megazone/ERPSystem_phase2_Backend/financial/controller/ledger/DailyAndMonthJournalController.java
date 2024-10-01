package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.DailyAndMonthJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DailyAndMonthJournalController {
    private final DailyAndMonthJournalService dailyAndMonthJournalService;

    @PostMapping("/api/financial/ledger/dailyAndMonthJournal/dailyShow")
    public ResponseEntity<Object> dailyShow(@RequestBody DailyAndMonthJournalSearchDTO dto) {
        Object tmp = dailyAndMonthJournalService.dailyLedgerShow(dto);
        return null;
    }
}
