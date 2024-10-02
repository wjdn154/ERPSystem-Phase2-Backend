package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.DailyAndMonthJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 일계표 월계표
 */
@RestController
@RequiredArgsConstructor
public class DailyAndMonthJournalController {
    private final DailyAndMonthJournalService dailyAndMonthJournalService;

    @PostMapping("/api/financial/ledger/dailyAndMonthJournal/dailyShow")
    public ResponseEntity<Object> dailyShow(@RequestBody DailyAndMonthJournalSearchDTO dto) {
        List<DailyAndMonthJournalShowDTO> showDTOs = dailyAndMonthJournalService.dailyLedgerShow(dto);
        return showDTOs != null ? ResponseEntity.status(HttpStatus.OK).body(showDTOs) :
        ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당하는 결과가 없습니다.") ;
    }
}
